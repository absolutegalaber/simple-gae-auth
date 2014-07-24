'use strict';
module.exports = function (grunt) {
    "use strict";
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        coffee: {
            compile: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/coffee/',
                        src: '**/*.coffee',
                        dest: 'src/main/webapp/app',
                        ext: '.js'
                    }
                ]
            },
            compileTests: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/test/coffee/',
                        src: '**/*.coffee',
                        dest: 'src/generated/',
                        ext: '.js'
                    }
                ]
            }
        },
        karma: {
            unit: {
                configFile: 'karma.conf.js',
                singleRun: true
            }
        },
        copy: {
            copyLib: {
                expand: true,
                src: [
                    'bower_components/angular/angular.js',
                    'bower_components/angular-resource/angular-resource.js',
                    'bower_components/angular-ui-router/release/angular-ui-router.js',
                    'bower_components/angular-bindonce/bindonce.js',
                    'bower_components/angular-bootstrap/ui-bootstrap.js',
                    'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
                    'bower_components/underscore/underscore.js'
                ],
                flatten: true,
                dest: 'src/main/webapp/lib'
            }
        }
    });

    grunt.registerTask('deployFrontend', [
        'copy:copyJs', 'copy:copyLib'
    ])

    grunt.registerTask('compileTests', [
        'coffee:compileTests', 'karma:unit'
    ]);
    grunt.registerTask('compileCoffee', [
        'coffee:compile', 'copy:copyLib'
    ]);
};
