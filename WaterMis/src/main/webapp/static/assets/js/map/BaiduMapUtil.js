/**
 * Get the location of the specific pump room through baidu geo search
 * Created by Administrator on 2016/10/25 0025.
 */
var dataLocation = {};
$(function () {
    var city = '丹阳市';

    var getLocation = function (point) {
        if (point) {
            dataLocation.push({
                // "NAME": pumpHouse["NAME"],
                "Longi": point.lng,
                "Lati": point.lat
            });
            console.log(point)
        }
    };

   $.getJSON(ROOT_PATH + "/static/assets/js/map/pump_house.json", function(result) {
       var pumpHouses = result["RECORDS"]

       if (pumpHouses && pumpHouses.length > 0) {
           var geoCoder = new BMap.Geocoder();
           var i = 0;
           for (i = 0; i < pumpHouses.length; i++) {
               if (dataLocation.hasOwnProperty(pumpHouses[i]["NAME"])) {
                   dataLocation[pumpHouses[i]["NAME"] + "#" + i] = pumpHouses[i];
               } else {
                   dataLocation[pumpHouses[i]["NAME"]] = pumpHouses[i];
               }
           }

           for (i = 0; i < pumpHouses.length; i++) {
               var pumpHouse = pumpHouses[i];
               (function () {
                   var name = pumpHouse["NAME"];
                   var index = i;
                   geoCoder.getPoint(city + pumpHouse["NAME"], function(point) {
                       if (point) {
                           dataLocation[name]["Longi"] = point.lng;
                           dataLocation[name]["Lati"] = point.lat;
                       } else {
                           console.log(name);
                       }
                   }, city);
               })();
           }
       }

   });
});

function getBaiduResult() {
    $.getJSON(ROOT_PATH + "/static/assets/js/map/baidu.json", function(result) {
        var location = [];
        for (var name in result) {
            if (result.hasOwnProperty(name)) {
                location.push(result[name]);
            }
        }
        console.log(JSON.stringify(location));
    });
}

function getArcgisResult() {
    $.getJSON(ROOT_PATH + "/static/assets/js/map/arcgis.json", function(result) {
        var location = [];
        for (var name in result) {
            if (result.hasOwnProperty(name)) {
                location.push(result[name]);
            }
        }
        console.log(JSON.stringify(location));
    });
}