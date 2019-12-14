const express = require('express')
const app = express();
const fs = require('fs')
const gm = require('gm').subClass({imageMagick: true})
const imagecolors = require('imagecolors');

// app.use(express.static('public'))

app.get('/', (req, res) => {
  res.send('Hello World!')
  // gm('/public/test.png').resize(5, 5)
  // res.sendFile('public/index.html')

  imagecolors.extract('/public/test.png', 6, function(err, colors){
      if (!err){
          console.log('EXTRACTED');
          console.log(colors);
          console.log();
      }


  // gm('test.png')
  //   .resize(250, 250)
  //   .colors(1)
  //   .toBuffer('RGB', function (error, buffer) {
  //       console.log(buffer.slice(0, 3));
  //   });
});

app.get('/color', (req, res) => {


  /**
   * extract predominant colors from image
   * note: maximum is currently capped at 96, need to do load testing before raising
   * usage: extract(imagePath, numColors, callback)
   */


      /**
       * convert colors to a custom palete
       * usage: convert(color_object, palette_json, callback)
       */
      // imagecolors.convert(colors, './palette.json', function(err, colors){
      //     if (!err){
      //         console.log('CONVERTED');
      //         console.log(colors);
      //     }
      // });
  });

});


app.listen(8001, () => {
  console.log('Example app listening on port 8000!')
});
