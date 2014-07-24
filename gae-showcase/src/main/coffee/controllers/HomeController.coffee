'use strict'
class HomeController
  constructor: ($scope)->
    console.log "CHECK !!!"
    $scope.check = 'NOW: Hotdeployed: Check da thing!'

HomeController.$inject = ['$scope']
angular.module('Showcase').controller 'HomeController', HomeController