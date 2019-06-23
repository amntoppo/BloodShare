# from tweepy import API
# from tweepy import Cursor
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
# from textblob import TextBlob

# import re
# import twitter_credentials
import numpy as np
# import pandas as pd
# import matplotlib.pyplot as plt
# plt.use('TkAgg')

import pyodbc
import json
import time
from dateutil import parser

ACCESS_TOKEN = "581748698-1LJbS70P4n6GtEP183RxHdIAheAq1OdKtLixp0CC"
ACCESS_TOKEN_SECRET = "pqlelRZjldMwudZsh6wW1mULeeGykz2huHsn9A1MyrjwK"
CONSUMER_KEY = "9dk7vTsNbET7e0YczCo3DInyY"
CONSUMER_SECRET = "aL0F3NgYU27nWGmNa2erWbBUTxpnlRjtV4tPJA7F7U8ej1kCOs"

server = 'tcp:tweets12.database.windows.net'
database = 'tweets'
username = 'adnan-azmat'
password = 'DreamDare321'

cnxn = pyodbc.connect('DRIVER={ODBC Driver 13 for SQL Server};SERVER='+server+';PORT=1443;DATABASE='+database+';UID='+username+';PWD='+ password)
# cnxn = pyodbc.connect('DRIVER={ODBC Driver 17 for SQL Server};SERVER='+server+';PORT=1443;DATABASE='+database+';UID='+username+';PWD='+ password)
cursor = cnxn.cursor()

t = time.time()

keywords = ["bloodrequired", "bloodneeded", "blooddonate", "blooddonation", "bloodbank"]
'''
class TwitterClient:
    def __init__(self, twitter_user=None):
        self.auth = TwitterAuthenticator().authenticate_twitter_app()
        self.twitter_client = API(self.auth)
        self.twitter_user = twitter_user

    def get_twitter_client_api(self):
        return self.twitter_client

    def get_user_timeline_tweets(self, num_tweets):
        tweets = []
        for tweet in Cursor(self.twitter_client.user_timeline, id=self.twitter_user).items(num_tweets):
            tweets.append(tweet)
        return tweets

    def get_friend_list(self, num_friends):
        friend_list = []
        for friend in Cursor(self.twitter_client.friends, id=self.twitter_user).items(num_friends):
            friend_list.append(friend)
        return friend_list

    def get_home_timeline_tweets(self, num_tweets):
        home_timeline_tweets = []
        for tweet in Cursor(self.twitter_client.home_timeline, id=self.twitter_user).items(num_tweets):
            home_timeline_tweets.append(tweet)
        return home_timeline_tweets
'''

class TwitterAuthenticator:
    def authenticate_twitter_app(self):
        auth = OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
        auth.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
        return auth


class TwitterStreamer:
    """
    Class for streaming and processing live tweets
    """
    def __init__(self):
        self.twitter_authenticator = TwitterAuthenticator()

    def stream_tweets(self, fetched_tweets_filename, hash_tag_list):
        # This handles Twitter authentication and the connection to the Twitter Streaming API
        listener = TwitterListener(fetched_tweets_filename)
        auth = self.twitter_authenticator.authenticate_twitter_app()
        stream = Stream(auth, listener)

        # This line filter Twitter Streams to capture data by keywords
        stream.filter(track=hash_tag_list)

    def stream_tweets(self, hash_tag_list):
        # This handles Twitter authentication and the connection to the Twitter Streaming API
        listener = TwitterListener()
        auth = self.twitter_authenticator.authenticate_twitter_app()
        stream = Stream(auth, listener)

        # This line filter Twitter Streams to capture data by keywords
        stream.filter(track=hash_tag_list)


class TwitterListener(StreamListener):
    """
    This is a basic listener class that just prints received tweets to Twitter
    """

    #def __init__(self, fetched_tweets_filename):
    #    self.fetched_tweets_filename = fetched_tweets_filename

    def on_data(self, raw_data):
        try:
            '''
            with open(self.fetched_tweets_filename, 'a') as tf:
                tf.write(raw_data)
            '''

            '''
            for row in cursor.tables():
                if row.table_name == 'Tweets':
                    print (row.table_name)
            '''

            data = json.loads(raw_data)
            if 'text' in data:
                tweet = data["text"]
                created_at = data["created_at"]
                user_location = data["user"]["location"]

                '''
                retweeted = data["retweeted"]
                username = data["user"]["screen_name"]
                user_tz = data["user"]["time_zone"]
                user_coordinates = data["coordinates"]
                '''
                '''
                print(retweeted)
                print(username)
                print(user_tz)
                print(user_location)
                print(user_coordinates)
                print(dir(raw_data))
                '''

            # Insert Query

            if user_location is not None:
                # "created_at": "Thu Oct 25 14:17:34 +0000 2018", %
                # 'Jun 1 2005  1:33PM', '%b %d %Y %I:%M%p'

                counter = []
                global keywords

                for disaster in keywords:
                    counter.append(tweet.count(disaster))
                a = np.array(counter)
                index = np.argmax(a)

                tsql = "INSERT INTO Tweets (Disaster, Location, Time) VALUES (?,?,?);"
                with cursor.execute(tsql, keywords[index], str(user_location), parser.parse(str(created_at))):
                    print('Successfuly Inserted!')

            # print(raw_data)
            print("\n")


        except BaseException as e:
            print("Error on data: %s" % str(e))

        global t

        if (time.time() - t) > 86400:
            cursor.execute("DELETE FROM Tweets WHERE Time < DATEADD(dd,-1,GETDATE());")
            t = time.time()

            '''
            if time.time() - t < 10:
                return True
            else:
            '''
            '''
            print('Reading data from table')
            tsql = "SELECT Text, Location FROM tweets;"
            print(time.time() - t)
            with cursor.execute(tsql):
                row = cursor.fetchone()
                while row:
                    print(str(row[0]) + " " + str(row[1]))
                    row = cursor.fetchone()
            '''
            '''
            return False
            '''

    def on_error(self, status_code):
        if status_code == 420:
            # Returning false on_data method in case rate limit occurs
            return False
        print(status_code)

'''
class TweetAnalyzer:
    """
    Functionality for analysing and categorizing content from tweets
    """

    def clean_tweet(self, tweet):
        return ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t])|(\w+:\/\/\S+)", " ", tweet).split())

    def analyze_sentiment(self, tweet):
        analysis = TextBlob(self.clean_tweet(tweet))

        if analysis.sentiment.polarity > 0:
            return 1
        elif analysis.sentiment.polarity == 0:
            return 0
        else:
            return -1

    def tweets_to_data_frame(self, tweets):

        df = pd.DataFrame(data=[tweet.text for tweet in tweets], columns=['tweets'])

        df['id'] = np.array([tweet.id for tweet in tweets])
        df['len'] = np.array([len(tweet.text) for tweet in tweets])
        df['date'] = np.array([tweet.created_at for tweet in tweets])
        df['source'] = np.array([tweet.source for tweet in tweets])
        df['likes'] = np.array([tweet.favorite_count for tweet in tweets])
        df['retweets'] = np.array([tweet.retweet_count for tweet in tweets])
        df['location'] = np.array([tweet.user.location for tweet in tweets])

        return df
'''

if __name__ == "__main__":
    # twitter_client = TwitterClient()
    # api = twitter_client.get_twitter_client_api()
    # tweet_analyzer = TweetAnalyzer()

    '''
    tweets = api.user_timeline(screen_name="realDonaldTrump", count=10)
    df = tweet_analyzer.tweets_to_data_frame(tweets)
    df['sentiment'] = np.array([tweet_analyzer.analyze_sentiment(tweet) for tweet in df['tweets']])
    '''

    hash_tag_list = ["bloodrequired", "bloodneeded", "blooddonate", "blooddonation", "bloodbank"]

    # fetched_tweets_filename = "tweets1.txt"
    # twitter_streamer.stream_tweets(fetched_tweets_filename, hash_tag_list)
    twitter_streamer = TwitterStreamer()
    twitter_streamer.stream_tweets(hash_tag_list)
    '''
    twitter_client = TwitterClient('pycon')
    print(twitter_client.get_user_timeline_tweets(1))
    '''

    # print(df.head(10))
    # Get average length over all tweets
    # print(np.mean(df['len']))

    # Time Series

    '''
    time_likes = pd.Series(data=df['likes'].values, index=df['date'])
    time_likes.plot(figsize=(16, 4), label="likes", legend=True)

    time_retweets = pd.Series(data=df['retweets'].values, index=df['date'])
    time_retweets.plot(figsize=(16, 4), label="retweets", legend=True)
    
    plt.show()
    '''

    '''
    for tweet in tweets:
        try:
            print(tweet.user.location)
        except AttributeError as e:
            continue
    '''
