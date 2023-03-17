const jwt = require('jsonwebtoken');
require("dotenv").config();
 const VerifyToken = (req,res,next) => {
      const token = req.headers.access_ticket;
      // console.log(req.headers.access_ticket)
      if(!token){

        return res.status(401).json({error:"not authenticated"});     
      }
      jwt.verify(token,process.env.AUTH_KEY,(err,user)=>{
        if(err){
        console.log(err);
        return res.status(403).json({error:"your token is not valid"});
        }
        req.user = user;
        next();
      })
}

module.exports = {VerifyToken};