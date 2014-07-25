'use strict'
class SignupController
  constructor: (@$scope)->
    @$scope.user = {}



SignupController.$inject = ['$scope']
angular.module('Showcase').controller 'SignupController', SignupController