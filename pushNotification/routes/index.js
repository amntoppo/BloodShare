var express = require('express');
var router = express.Router();

var admin = require("firebase-admin");

var serviceAccount = require("../bloodshare.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://bloodshare-263a1.firebaseio.com"
});

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/submit-form', (req, res) => {
  var name = req.body.name;
  var group = req.body.group;
  var location = req.body.location;
  console.log(name + group + location);

//   var message = {
//     data: {
//         title: 'Blood Required',
//
//         body: group + ' blood urgently needed at ' + location
//     },
//     topic: group
// };
    var payload = {
        notification: {
            title: "Blood Required",
            body: group + " blood urgently needed at " + location
        }
    };
    var topic = group;
  // admin.messaging().send(message)
  //     .then((response) => {
  //       console.log("sent", response);
  //     })
  //     .catch((error) => {
  //       console.log("error", error);
  //     });

    admin.messaging().sendToTopic(topic, payload)
        .then(function(response) {
            console.log("sent", response);
        })
        .catch(function(error) {
            console.log("error", error);
        });
  res.end()
});
module.exports = router;
