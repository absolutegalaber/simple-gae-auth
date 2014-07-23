showCase = angular.module('Showcase', ['ngResource', 'ui.router'])

showCase.config ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider)=>
  $stateProvider.state 'home',
    url: '/'
    templateUrl: 'templates/home.html'
    controller:'HomeController'

    $urlRouterProvider.otherwise '/'

]