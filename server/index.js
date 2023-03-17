const express = require('express')
const morgan = require('morgan')
const path = require('path')
const app = express();
const fs = require('fs');
const https = require('https');   
const cors = require('cors')
const port = 5500
const db = require('./config/db')
const route = require('./router')
const cookieParser = require('cookie-parser');
require("dotenv").config();
//connect
db.connect();
app.use(cookieParser())
app.use(cors({  
  origin: ["http://localhost:3000"],
  credentials: true }));
  app.use(express.json());
  app.use(express.urlencoded({ extended: false,limit: '50mb',parameterLimit: 1000000}));

app.set('view engine', 'ejs');
//app.set('views',path.join(__dirname,'resource/views'))

app.use(morgan('tiny'))
app.use(express.static('public'))
console.log(path.join(__dirname,'public/'))
route(app);
// const sslServer = https.createServer({
//   key:fs.readFileSync(path.join(__dirname,'cert','key.pem')),
//   cert:fs.readFileSync(path.join(__dirname,'cert','cert.pem')),
// },app)
// sslServer.listen(5000,()=>{
//   console.log("HTTPS SERVER")
// })
// route(app);
app.listen(port, () => {
  console.log(` app listening on port ${port}`)
})

