
const CreateRes = (data,message) => {
       const store = {
          
          message,
          status:200,
          success:true,
       }
       return store;

}
module.exports = CreateRes;

// call.enqueue(new Callback<Res>() {
//     @Override
//     public void onResponse(Call<Res> call, Response<Res> response) {
//         if(response.code() >= 400) {
//             Toast.makeText(DangKyActivity.this, "Fail", Toast.LENGTH_SHORT).show();
// //                                Log.w(null,response.body().toString());
//             return;
//         }
//         Toast.makeText(DangKyActivity.this, "Success", Toast.LENGTH_SHORT).show();
//     }

//     @Override
//     public void onFailure(Call<Res> call, Throwable t) {
//         t.printStackTrace();
//     }
// });