from datetime import datetime
from datetime import timedelta
import json
import random
import sys


if len(sys.argv) > 1:
	N = int(sys.argv[1])
else:
	N = int(raw_input("How many tweets do you want to generate? "))

rateLimitMessage = """{"limit":{"track":253,"timestamp_ms":"1459231486932"}}"""

tweet = """{  
   "created_at":"Mon Mar 28 23:23:12 +0000 2016",
   "id":714593712366620672,
   "id_str":"714593712366620672",
   "truncated":false,
   "in_reply_to_status_id":714593258802905089,
   "in_reply_to_status_id_str":"714593258802905089",
   "in_reply_to_user_id":154304713,
   "in_reply_to_user_id_str":"154304713",
   "in_reply_to_screen_name":"gbvigo",
   "user":{  
      "id":1486482758,
      "id_str":"1486482758",
      "name":"raphaella",
      "screen_name":"voudaropapo",
      "location":"Rio de Janeiro, Brasil",
      "url":null,
      "protected":false,
      "verified":false,
      "followers_count":394,
      "friends_count":287,
      "listed_count":0,
      "favourites_count":9598,
      "statuses_count":29616,
      "created_at":"Thu Jun 06 01:15:57 +0000 2013",
      "utc_offset":null,
      "time_zone":null,
      "geo_enabled":true,
      "lang":"pt",
      "contributors_enabled":false,
      "is_translator":false,
      "profile_background_color":"000000",
      "profile_background_image_url":"http:\/\/abs.twimg.com\/images\/themes\/theme18\/bg.gif",
      "profile_background_image_url_https":"https:\/\/abs.twimg.com\/images\/themes\/theme18\/bg.gif",
      "profile_background_tile":false,
      "profile_link_color":"F5ABB5",
      "profile_sidebar_border_color":"000000",
      "profile_sidebar_fill_color":"000000",
      "profile_text_color":"000000",
      "profile_use_background_image":false,
      "profile_image_url":"http:\/\/pbs.twimg.com\/profile_images\/712411700436320256\/V0FuQVuG_normal.jpg",
      "profile_image_url_https":"https:\/\/pbs.twimg.com\/profile_images\/712411700436320256\/V0FuQVuG_normal.jpg",
      "profile_banner_url":"https:\/\/pbs.twimg.com\/profile_banners\/1486482758\/1459036011",
      "default_profile":false,
      "default_profile_image":false,
      "following":null,
      "follow_request_sent":null,
      "notifications":null
   },
   "geo":null,
   "coordinates":null,
   "place":{  
      "id":"97bcdfca1a2dca59",
      "url":"https:\/\/api.twitter.com\/1.1\/geo\/id\/97bcdfca1a2dca59.json",
      "place_type":"city",
      "name":"Rio de Janeiro",
      "full_name":"Rio de Janeiro, Brasil",
      "country_code":"BR",
      "country":"Brasil",
      "bounding_box":{  
         "type":"Polygon",
         "coordinates":[  
            [  
               [  
                  -43.795449,
                  -23.083020
               ],
               [  
                  -43.795449,
                  -22.739823
               ],
               [  
                  -43.087707,
                  -22.739823
               ],
               [  
                  -43.087707,
                  -23.083020
               ]
            ]
         ]
      },
      "attributes":{  

      }
   },
   "contributors":null,
   "is_quote_status":false,
   "retweet_count":0,
   "favorite_count":0,
   "entities":{  
      "hashtags":[  

      ],
      "urls":[  

      ],
      "user_mentions":[  
         {  
            "screen_name":"gbvigo",
            "name":"gab.",
            "id":154304713,
            "id_str":"154304713",
            "indices":[  
               0,
               7
            ]
         }
      ],
      "symbols":[  

      ]
   },
   "favorited":false,
   "retweeted":false,
   "filter_level":"low",
   "lang":"pt",
   "timestamp_ms":"1459207392194"
}"""
tweet = json.loads(tweet)

format = "%a %b %d %H:%M:%S +0000 %Y"

outputValues = {
	1: {"v": 0.0, "e": 0},
	2: {"v": 2.0, "e": 2},
	3: {"v": 3.0, "e": 6},
	4: {"v": 4.0, "e": 12},
	5: {"v": 5.0, "e": 20},
}

hashtags = {
	1: [],
	
	2: [{"text":"a","indices":[9,18]},
		{"text":"b","indices":[9,18]}],
	
	3: [{"text":"c","indices":[9,18]},
		{"text":"d","indices":[46,60]},
		{"text":"e","indices":[46,60]}],
		
	4: [{"text":"f","indices":[9,18]},
		{"text":"g","indices":[46,60]},
		{"text":"h","indices":[46,60]},
		{"text":"i","indices":[46,60]}],
		
	5: [{"text":"j","indices":[9,18]},
		{"text":"k","indices":[46,60]},
		{"text":"l","indices":[46,60]},
		{"text":"m","indices":[46,60]},
		{"text":"n","indices":[46,60]}]
}

[{"text":"Pisteare","indices":[9,18]},{"text":"elsientometro","indices":[46,60]}]
selected = set()

inputPath = "./../insight_testsuite/tests/test-random-tweets/tweet_input/tweets.txt"
outputPath = "./../insight_testsuite/tests/test-random-tweets/tweet_output/output.txt"
with open(inputPath, "w") as tweets, open(outputPath, "w") as output:
	for i in xrange(N):
		# Roughly 1 out of 20 times  is a rate limit message, don't write output
		if random.random() < .05:
			tweets.write(rateLimitMessage + "\n")
			
		else:
			#Roughly 1 out of 10 times we jump ahead one day in time
			if (random.random() < .1):
				ts = datetime.strptime(tweet['created_at'], format)
				ts += timedelta(1)
				ts = ts.strftime(format)
				tweet['created_at'] = ts
				# Dump selected set
				selected = set()
				
			randomSelect = random.randint(1,5)
		
			# Select a random hashtag from the collection
			hashtag = hashtags[randomSelect]
			tweet['entities']['hashtags'] = hashtag
			tweets.write(json.dumps(tweet) + "\n")
			
			selected.add(randomSelect)
		
			# Calculate the current output value
			v, e = 0, 0
			for x in selected:
				v += outputValues[x]["v"]
				e += outputValues[x]["e"]
			if v > 0:
				outputValue = e/v
			else:
				outputValue = 0.0
			outputValue = int(outputValue*100)/100.0
			output.write(("%.2f" % outputValue) + "\n")
			