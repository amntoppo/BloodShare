import json
import urllib.request

def remove_char(str, n):
    first_part = str[:n]
    last_part = str[n + 1:]
    return first_part + last_part

A = []

while 1:
    x = {
      "name": input("Enter Name: "),
      "group": input("Enter Blood Group: "),
      "location": input("Enter Location: ")
    }

    y = json.dumps(x)
    A.append(y)

    a = input("Continue (Y/N)")
    if (a=='Y'):
        continue
    else:
        break

a = ""
a = a + '['

for i in A:
    a = a + i + ','

a = a + ']'
a = remove_char(a, len(a)-2)
print (a)
d = json.loads(a)

myurl = 'https://www.jsonstore.io/39770b8f97967acf30d78895d2a74720bccb66b8683fae9f9331633c3ad1af13'
req = urllib.request.Request(myurl)
req.add_header('Content-Type', 'application/json; charset=utf-8')
jsondata = json.dumps(d)
jsondataasbytes = jsondata.encode('utf-8')
req.add_header('Content-Length', len(jsondataasbytes))
print (jsondataasbytes)
response = urllib.request.urlopen(req, jsondataasbytes)


print (d)