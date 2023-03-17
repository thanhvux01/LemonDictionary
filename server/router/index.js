
const express = require('express');
const authRoute = require('./auth.js');
const wordRoute = require('./word')
const cors = require("cors")

function route(app) {
   
   app.use('/api/auth',authRoute);
   app.use('/api/word',wordRoute);
   app.use((err,req,res,next)=>{
        
        const status = err.status || 500;
        const message = err.message || "Critical Error";
        return res.status(status).json({message:message,success:false,status:status});
    
   })

   /*goi den homepageroute , homepageroute se xuat ra 1 router */

}

module.exports = route;