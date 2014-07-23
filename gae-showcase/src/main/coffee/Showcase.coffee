showCase = angular.module('Showcase', ['ngResource', 'ui.router'])
showCase.config ['$stateProvider', ($stateProvider)=>
  $stateProvider.state 'home'

]