variable "access_key" {
    type = string
}
variable "secret_key" {
    type = string
}
variable "jwt_key" {
    type = string
}
variable "database_password" {
    type = string
}
variable "database_name" {
    type = string
    default = "job_postings"
}
variable "database_username" {
    type = string
}
variable "admin_username" {
    type = string
}
variable "admin_password" {
    type = string
}
provider "aws" {
    region="us-east-2"
    access_key=var.access_key
    secret_key=var.secret_key
}
resource "aws_db_instance" "db" {
    allocated_storage = 100
    engine = "postgres"
    engine_version = 11.5
    identifier = "database"
    instance_class = "db.t2.micro"
    password = var.database_password
    username = var.database_username
    skip_final_snapshot = true
    storage_encrypted = false
    name = var.database_name
}
resource "aws_elastic_beanstalk_application" "application" {
    name="job-postings"
}
resource "aws_elastic_beanstalk_environment" "environment" {
    name="job-postings-environment"
    application = aws_elastic_beanstalk_application.application.name
    solution_stack_name = "64bit Amazon Linux 2 v3.4.4 running Docker"
    setting {
        namespace = "aws:autoscaling:launchconfiguration"
        name = "IamInstanceProfile"
        value = "aws-elasticbeanstalk-ec2-role"
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "SPRING_DATASOURCE_URL"
        value = "jdbc:postgresql://${aws_db_instance.db.endpoint}/${var.database_name}"
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "SPRING_DATASOURCE_PASSWORD"
        value = var.database_password
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "SPRING_DATASOURCE_USERNAME"
        value = var.database_username
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "JWT_KEY"
        value = var.jwt_key
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "ADMIN_USERNAME"
        value = var.admin_username
    }
    setting {
        namespace = "aws:elasticbeanstalk:application:environment"
        name = "ADMIN_PASSWORD"
        value = var.admin_password
    }
}