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
                        dest: 'src/generated/',
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
            shopApp: {
                expand: true,
                cwd: 'src/generated/',
                src: ['**'],
                dest: 'build/shop/js/app',
                flatten: false,
                filter: 'isFile'
            },
            shopLib: {
                expand: true,
                src: [
                    'bower_components/angular/angular.js',
                    'bower_components/angular-resource/angular-resource.js',
                    'bower_components/angular-mocks/angular-mocks.js',
                    'bower_components/angular-ui-router/release/angular-ui-router.js',
                    'bower_components/angular-translate/angular-translate.js',
                    'bower_components/angular-bindonce/bindonce.js',
                    'bower_components/angular-bootstrap/ui-bootstrap.js',
                    'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
                    'bower_components/underscore/underscore.js',
                    'bower_components/angular-translate-loader-static-files/angular-translate-loader-static-files.js',
                    'bower_components/tinymce-release/tinymce.min.js',
                    'bower_components/ngUpload/ng-upload.js',
                    'bower_components/hello/dist/hello.all.js'
                ],
                flatten: true,
                dest: 'build/shop/js/lib'
            },
            shopMarkup: {
                expand: true,
                cwd: 'src/main/markup/',
                src: [
                    '**'
                ],
                flatten: false,
                dest: 'build/shop/'
            },
            copyShopFonts: {
                expand: true,
                src: ['bower_components/bootstrap-sass/fonts/*'],
                dest: 'build/shop/fonts',
                flatten: true,
                filter: 'isFile'
            }
        },
        compass: {
            compileShop: {
                options: {
                    sassDir: 'src/main/sass',
                    cssDir: 'build/shop/styles'
                }
            }
        }
    });

    grunt.registerTask('compileTests', [
        'coffee:compileTests', 'karma:unit'
    ]);
    grunt.registerTask('compileCoffee', [
        'coffee:compile'
    ]);
    grunt.registerTask('assemble', [
        'coffee:compile', 'compass:compileShop', 'copy:shopApp', 'copy:shopLib', 'copy:shopMarkup', 'copy:copyShopFonts'
    ]);
};
