'use strict'
showCase = angular.module('Showcase', ['ngResource', 'ui.router'])

showCase.config ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider)=>

  #
  # Home
  #
  $stateProvider.state 'home',
    url: '/'
    templateUrl: 'templates/home.html'

  #
  # Signup
  #
  $stateProvider.state 'signup',
    url: '/signup'
    templateUrl: 'templates/signup.html'

  $urlRouterProvider.otherwise '/'
]