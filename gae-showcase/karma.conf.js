// Karma configuration
// Generated on Thu Sep 26 2013 21:58:06 GMT-0700 (PDT)

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '.',

        // frameworks to use
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            'src/main/app/**/*.js'
//            'bower_components/underscore/underscore.js',
//            'bower_components/angular/angular.js',
//            'bower_components/angular-resource/angular-resource.js',
//            'bower_components/angular-mocks/angular-mocks.js',
//            'bower_components/angular-ui-router/release/angular-ui-router.js',
//            'bower_components/angular-translate/angular-translate.js',
//            'bower_components/angular-bindonce/bindonce.js',
//            'bower_components/angular-bootstrap/ui-bootstrap.js',
//            'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
//            'bower_components/angular-translate-loader-static-files/angular-translate-loader-static-files.js',
//            'bower_components/tinymce-release/tinymce.min.js',
//            'bower_components/angular-ui-tinymce/src/tinymce.js',
//            'bower_components/ngUpload/ng-upload.js',
//            'bower_components/hello//dist/hello.all.js',
//            'src/main/markup/**/*.html',
//            'src/main/generated/**/*.js',
//            'src/test/js/**/*.js',
//            'src/test/generated/**/*.js'
        ],

        // list of files to exclude
        exclude: [
        ],

        // test results reporter to use
        // possible values: 'dots', 'progress', 'junit', 'growl', 'coverage'
        reporters: ['progress', 'junit', 'html', 'coverage', 'spec'],

        junitReporter: {
            outputFile: 'build/test-results.xml'
        },

        coverageReporter: {
            type: 'html',
            dir: 'build/coverage/'
        },

        // web server port
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
//        browsers: ['Chrome'],
        browsers: ['PhantomJS'],

        // If browser does not capture in given timeout [ms], kill it
        captureTimeout: 60000,

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: true,

        preprocessors: {
            'src/main/generated/**/*.js': ['coverage']
        }
//        ,
//
//        ngHtml2JsPreprocessor: {
//            stripPrefix: 'public/'
//        }
    });
};
