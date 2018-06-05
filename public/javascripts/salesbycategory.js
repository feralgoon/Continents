var labels = document.getElementById("chartData").getAttribute("data-labels").split(",");
var data = document.getElementById("chartData").getAttribute("data-data").split(",");
var ctx = document.getElementById("sales").getContext('2d');
var myChart = new Chart(ctx, {
   type: 'bar',
   data: {
       labels: labels,
       datasets: [{
           label: 'Total Sales Per Category',
           data: data,
           borderWidth: 2,
           borderColor: 'rgba(0,0,0,1)',
           backgroundColor: 'rgba(200,120,225,0.8)'
       }]
   },

   options: {
     scaleShowValues: true,
     scales: {
       yAxes: [{
         ticks: {
           beginAtZero: true
         }
       }],
       xAxes: [{
         ticks: {
           autoSkip: false
         }
       }]
     }
   }
});