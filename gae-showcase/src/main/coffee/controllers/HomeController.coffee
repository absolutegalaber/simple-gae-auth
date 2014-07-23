'use strict'
class HomeController
  constructor: ($scope)->
    console.log "CHECK !!!"
    $scope.check = 'Check'

HomeController.$inject = ['$scope']
angular.module('Showcase').controller 'HomeController', HomeController