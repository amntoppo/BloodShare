
//var admin = require('Fireabnpm install firebase-admin --save')
var admin = require('firebase-admin');
console.log("works?");
const name = $("#name");
const group = $("#group");

//const hospital = $("#exampleHospital");
console.log("now?");

function onClick() {

    console.log("clicked");
    //location.href = "home.html";
    var test = name.val();
    var topic = group.val();
    console.log($("#group").val());
    var message = {
        data: {
            title: 'Blood Required',

            body: $("#group").val() + ' blood urgently needed at '
        },
        topic: $("#group").val()
    };
    //const admin = firebase.messaging();
    admin.messaging().send(message)
        .then((response) => {
            console.log('Sent', response);
        })
        .catch((error) => {
                console.log('error', error);
            }
        )
}


// $("#pushit").on("click", function(){
// console.log('error', "error");
// location.href = "home.html";
//
// $("#contactForm").addClass("hide");
// var topic = group.val();
// var message = {
//     data: {
//         title: 'Blood Required',
//
//         body: group.val() + ' blood urgently needed at ' + hospital.val()
//     },
//     topic: topic
// };
// //const admin = firebase.admin();
// firebase.messaging().send(message)
//     .then((response) => {
//         console.log('Sent', response);
//     })
//     .catch((error) => {
//         console.log('error', error);
//         }
//     )
// });