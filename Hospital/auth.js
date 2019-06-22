
  // Initialize Firebase
  var config = {
      apiKey: "AIzaSyA8OAjfcbEEDG1_puMZLb40uqqCVl5a930",
      authDomain: "hospitalreg-4af01.firebaseapp.com",
      databaseURL: "https://hospitalreg-4af01.firebaseio.com",
      projectId: "hospitalreg-4af01",
      storageBucket: "hospitalreg-4af01.appspot.com",
      messagingSenderId: "576025573190",
      appId: "1:576025573190:web:0a8eaec5ebd2b3ae"
  };
  firebase.initializeApp(config);
  // Save a new recommendation to the database, using the input in the form


  // get stored values
  const txtEmail =$("#txtEmail");
  const txtPassword=$("#txtPassword");
  const btnLogin=$("#btnLogin");
  const btnSignUp=$("#btnSignUp");
  const btnLogout=$("#btnLogout");
  const auth = firebase.auth();

  // add event listeners

  $("#btnLogin").on("click", function(){

    //  Get Email and password
    const email = txtEmail.val();
    const pass = txtPassword.val();
    // const auth = firebase.auth();

    //Sign in
    const promise = auth.signInWithEmailAndPassword(email, pass);
    promise.catch(e => console.log(e.message));



  });

  // add sign up event 
  $("#btnSignUp").on("click", function(){
      
    //  Get Email and password
    const email = txtEmail.val();
    const pass = txtPassword.val();
    // const auth = firebase.auth();

    //Sign in 
    const promise = auth.createUserWithEmailAndPassword(email, pass);
    promise.catch(e => console.log(e.message));

  });

  // add a logout btn event listener
  $("#btnLogout").on("click", function(){
    auth.signOut()
  })

  // add a real time authentication listener
  auth.onAuthStateChanged(firebaseUser => {
      if(firebaseUser) {
          console.log(firebaseUser);
          // location.href = "register.html";

          $("#DDD").addClass("hide");
          $("#btnLogout").removeClass("hide");
          $("#btnLogin").addClass("hide");
          $("#btnSignUp").addClass("hide");
          $("#DD").removeClass("hide");

      }
      else {
          console.log("Not Logged In")
          $("#btnLogout").addClass("hide");
          $("#btnLogin").removeClass("hide");
          $("#btnSignUp").removeClass("hide");
          $("#DDD").removeClass("hide");
          $("#DD").addClass("hide");

          console.log(window.location.href)
          if (window.location.href == "file:///Users/adnanazmat/JharStart/Firebase-User-Authentication/register.html")
              location.href = "home.html";
      }
  })

