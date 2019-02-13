console.log("mdConverter.js initialized");
var showdown = require('marked');
let parent = document.getElementById('introduction');

var text = "- Hello there",
      target = document.getElementById('introduction'),
      converter = new showdown.Converter(),
      html = converter.makeHtml(text);
    
    target.innerHTML = html;

