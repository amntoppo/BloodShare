#ACb74deb55d47beeaa910f21a3b5020675
# account_sid
#cb2547996bc629577ec8b5965ea13663
# auth_token
# ef24478e6f62ee28577b1becd87d64e8
#TEST AUTHTOKEN

from twilio.rest import Client

account_sid = 'ACb74deb55d47beeaa910f21a3b5020675'
auth_token = 'cb2547996bc629577ec8b5965ea13663'

client = Client(account_sid, auth_token)

client.messages.create(
    to = "+917070241542",
    from_ = "+18594487713",
    body = "B +ve blood required at RIMS"
)
