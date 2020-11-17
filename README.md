# AWS AppConfig Java Sample

## **Overview**

This is a demo of the [AWS AppConfig](https://docs.aws.amazon.com/appconfig/latest/userguide/what-is-appconfig.html) Java application as explained in the blog post [Application configuration deployment to container workloads using AWS AppConfig](https://aws.amazon.com/blogs/mt/application-configuration-deployment-to-container-workloads-using-aws-appconfig). This demo shows how to integrate a Java Microservices application with AWS AppConfig service along with implementing an in-memory cache to efficiently manage the application configuration.

[AWS AppConfig](https://docs.aws.amazon.com/appconfig/latest/userguide/what-is-appconfig.html) helps AWS customers to quickly roll out application configurations across applications hosted on EC2 instances, containers, AWS Lambda, mobile apps, IoT devices, and on-premise servers in a validated, controlled and monitored way.

This demo explains


1. How to separate application configuration from application code for a containerized application.
2. Use AWS AppConfig to manage and deploy the application configuration.
3. How to automate and efficiently manage application configurations in a containerized application.
 

This demo uses Cloudformation templates to deploy an [Amazon Elastic Container Service](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/Welcome.html) Cluster and a [AWS Fargate](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/AWS_Fargate.html) Task. Users would clone this repository, build a docker image and push to [Amazon Elastic Container Registry](https://docs.aws.amazon.com/AmazonECR/latest/userguide/what-is-ecr.html) and interact with the AWS AppConfig Service.

This application has a Caching layer built in to cache the responses from AWS AppConfig Service. The Subsequent calls to fetch the configuration value checks the cache first and returns the response from the cache. If the cache is empty, then it makes a call to AWS AppConfig API to fetch the value. The Cache expiry is based on TTL set in properties.

## **Installation Instructions**

### **STEP 1: Create application, environments and configuration profile in AWS AppConfig**

1. To get started, go to [AWS AppConfig](https://console.aws.amazon.com/systems-manager/appconfig) on the [AWS Console](https://console.aws.amazon.com/console/home)
2. On the [AppConfig console](https://console.aws.amazon.com/systems-manager/appconfig) , click Create Configuration Data and specify the name of the application. You can add an optional description and apply tags to the application.
3. Once the application is created, you will then be redirected to a page with Environments and Configuration Profiles tabs. Let’s start by creating an environment by clicking Create environment and specifying the environment name and optional description. You can also optionally add tags and configure CloudWatch alarms for this environment.
4. Next, let’s set up a configuration profile. Select the Configuration Profiles tab, and click Create configuration profile. Provide the name of the configuration profile and an optional description and click Next.
5. Next, select *AWS AppConfig hosted configuration* as the configuration source and specify the content of the configuration data. Within AWS AppConfig hosted configurations, you can store configurations either as text or in JSON or YAML formats. For the purposes of this blog, we will create an application configuration in JSON  with two parameters which will be used in the Container application code.

```
    {
      "boolEnableFeature": true,
      "intItemLimit": 5
    }
```

1. Clicking Next, you can optionally add validators to validate the configuration. More information on adding validators can be found [here](https://docs.aws.amazon.com/appconfig/latest/userguide/appconfig-creating-configuration-and-profile-validators.html). Next, create the configuration by clicking Create configuration profile.
2. Next, you can deploy the configuration by clicking Start deployment
3. Choose the Environment , Hosted Configuration Version, Deployment Strategy and an Optional Description to Start the deployment process.
4. You can either create a custom Deployment Strategy by clicking Create Deployment Strategy or select one of the pre-defined Deployment Strategies provided by AWS AppConfig. You can read more about the components of the deployment strategies [here](https://docs.aws.amazon.com/systems-manager/latest/userguide/appconfig-creating-deployment-strategy.html). For the purposes of this demo, we are selecting the “*AppConfig.Linear50PercentEvery30Seconds*” deployment strategy.



### STEP 2: Clone the Repository

1. Git clone this Repository

### STEP 3: Containerize the Java Microservice into a Docker container, publish to Amazon ECR and then deploy the Container application into AWS ECS Fargate

1. Create base application stack using AWS CloudFormation

    * Open [CloudFormation console](https://console.aws.amazon.com/cloudformation/home) and click on “Create stack”, selecting “With new resources” option.
    * In the next screen, under the “Specify template” section choose “Upload template file“, and provide the file you downloaded from the repo */templates/ecs-cluster.yml.*
    * Click next, give the stack a name like “ECSCluster-dev“, and choose dev as value for the Environment parameter.Click next, optionally define your tags, and click next again. On the last screen, don’t forget to tick the check box in the “Capabilities” section, and finally click on “Create stack” button.

1. Upload the Project Image to Amazon Elastic Container Repository

    *   Open the Amazon Elastic Container repository created from previous step and choose "View push commands".
    *   Follow the steps displayed to upload the project (these steps work only when you use AWS CLI version 1.7 or later).
    *   When the upload completes, copy the URL of the image in the repository. Use this URL as a Input parameter to the cloud formation template described in the section section.



1. Create Fargate Task, ECS Service and Load Balancer using AWS CloudFormation

    * Open [CloudFormation console](https://console.aws.amazon.com/cloudformation/home) and click on “Create stack”, selecting “With new resources” option.
    * In the next screen, under the “Specify template” section choose “Upload template file“, and provide the file you downloaded from the repo */templates/fargate-task.yml.*
    * Click next, give a name to the stack like “fargate-task-dev”. Choose “dev” as value for the Environment parameter. Provide the image URL obtained in the previous step for the ImageUrl parameter and leave the rest of the parameters as default. Click next and optionally define your tags. Click next again. On the last screen, don’t forget to tick the check box in the “Capabilities” section, and finally click on “Create stack” button.

### STEP 4 : Verify the deployed application, update the AppConfig Configuration data and deploy configuration to the application

1. Navigate to [AWS CloudFormation Console](https://console.aws.amazon.com/cloudformation/home) and open the fargate-task-dev stack you created
2. Click on Outputs and copy the ExternalUrl for the Loadbalancer
3. Verify the application by using the External URL for the Load Balancer. *http://<load balancer dns>/movies/getMovies*
4. Next, we will change the configuration value in the AWS AppConfig and see how it will be reflected in the container application.
5. Open the [AWS AppConfig console](https://console.aws.amazon.com/systems-manager/appconfig) ,  click on your Application and go to Configuration Profiles tab and click on the Configuration Profile you created
6. Click on Create under Hosted configuration versions, this will open a new screen where you can edit the Configuration data.
7. Edit the Configuration value and click Create hosted configuration version button.
8. Next, click start deployment and choose the Environment, latest hosted configuration version, Deployment Strategy and an optional description to start the deployment process.
9. Once the deployment is complete, visit the application URL again to see the changes reflected immediately.
10. Note that this change did not require the container application to be restarted since the application retrieved the updated value in the subsequent call to AWS AppConfig.

## Cleanup

Delete all the resources created in throughout this process and prevent additional costs.

**AppConfig**

1. Hosted configuration
2. Configuration Profile
3. Environment
4. Application

**Base Container Application and Fargate Task**

1. Navigate to [AWS CloudFormation Console](https://console.aws.amazon.com/cloudformation/home)
2. Select the fargate-task-dev stack and click delete
3. Select the ECSCluster-dev stack and click delete

## Licence

This sample code is licensed under the MIT-0 License. See the LICENSE file.
