blackboardApp.controller('HomeController', function ($scope, userService, $state) {
	
	$scope.status = 'login';
	$scope.isStudent;
	$scope.schoolName;
    $scope.setStatus = function(status){
        $scope.status = status;
    };
    $scope.getStatus = function(){
        return $scope.status;  
    };

    $scope.student = new Student;

    //sign up for a student
    $scope.doSignup = function (){
    	if ($scope.isStudent == "false") {
	    	$scope.student.isStudent = 0;
	    }
	    $scope.student.schoolId = GlobalSchool.indexOf($scope.schoolName) + 1;

		console.log($scope.student);

		userService.signup($scope.student).then(function(res) {
			console.log(res);
			//$state.go('home');
			$scope.status = 'login';
		})
	}

	//login for a student
	$scope.doLogin = function() {

		userService.login($scope.student).then(function(res){
			console.log("finis");
			console.log(res);
			if (res) {
				userService.getCourse($scope.student).then(function(res){
					console.log("finis");
					console.log(res);
					$state.go('course');
				});
				
			}
		});
		

	}
});
