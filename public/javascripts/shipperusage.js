var labels = document.getElementById("chartData").getAttribute("data-labels").split(",");
var data = document.getElementById("chartData").getAttribute("data-data").split(",");
var ctx = document.getElementById("shippers").getContext('2d');
var coloR = [];
var dynamicColors = function() {
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgb(" + r + "," + g + "," + b + ")";
         };
 for (var i in data) {
             coloR.push(dynamicColors());
          }
var myChart = new Chart(ctx, {
   type: 'doughnut',
   data: {
       labels: labels,
       datasets: [{
           label: 'Shippers',
           data: data,
           borderWidth: 2,
           borderColor: 'rgba(0,0,0,1)',
           backgroundColor: coloR //'rgba(200,120,225,0.8)'
       }]
   },

   options: {
     scaleShowValues: false
   }
});