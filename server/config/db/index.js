const mongoose = require('mongoose');

async function connect() {
   try{
     
    await mongoose.connect('mongodb://localhost:27017/Lemon',{
        useNewUrlParser: true,
        useUnifiedTopology: true
    });
    console.log("Connect database successfully!!!")
   }
   catch(error){
    handleError(error);
   }

}

module.exports = { connect };