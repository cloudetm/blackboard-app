//define the global school
var GlobalSchool = ["The George Washington University", "George Mason University", "Georgetown University", "Moses University"];


function User() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.password = "";
    this.schoolId = 0;
}

function Student() {
    User.call(this);
    this.isStudent = 1;
    //isStudent is true means it's a student or it's a instructor.
}
Student.prototype = Object.create(User.prototype);

function School() {
    this.name = "";
    this.id = 1;
}

function Course() {
    this.courseId = 0;
    this.User = new User();
    this.School = new School();
    this.courseName = "";
    this.subject = "";
    this.courseNumber = 0;
    this.credits = 0;
    this.syllabusFileName = "";
    this.maxCapacity = 0;
}

function Transcript() {
    this.transcriptId = 0;
    this.studentEmail = "";
    this.semester = "";
    this.year = "";
    this.course = new Course();
    this.grade = 0;
}


var blackboardApp = angular.module('blackboardApp', ['ui.router']);

blackboardApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: '/app/view/home.html',
        controller: 'HomeController'
    });
    $stateProvider.state('signup', {
        url: '/signup',
        templateUrl: '/app/view/signup.html',
        controller: 'SignupController'
    });
    $stateProvider.state('course', {
        url: '/course',
        templateUrl: '/app/view/course.html',
        controller: 'CourseController'
    });
});



blackboardApp.directive('footer', function() {
    return {
        restrict: 'A', //This menas that it will be used as an attribute and NOT as an element. I don't like creating custom HTML elements
        replace: true,
        scope: {}, // This is one of the cool things :). Will be explained in post.
        templateUrl: "/app/view/footer.html",
        controller: FooterController       
    };
    function FooterController($window,$scope){
        
        
    };
});