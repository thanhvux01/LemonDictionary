const DateToString = () => {
    try{
    const date = new Date();
    const day = date.getUTCDate();
    const month = date.getMonth() +1;
    const year = date.getFullYear();
    const format = year+"-"+month+"-"+day;
    return format;
    }catch(err){
        console.log(err);
    }
}


module.exports = {DateToString};