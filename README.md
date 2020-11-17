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

1. Open the AWS Systems Manager console.
2. In the left navigation pane, choose AWS AppConfig.
3. If the AWS AppConfig welcome page appears, click Create configuration data. Otherwise, click Create application.
4. For Name, enter a name for the application. (*MyContainerApplication*) You can add an optional description and apply tags to the application. Choose Create application.
5. After the application is created, you are directed to a page with Environments and Configuration Profiles Choose Create environment, and then enter a name (MyContainerApplicationProductionEnvironment) and optional description for the environment. You can also optionally add tags and configure Amazon CloudWatch alarms for this environment.
6. In the top navigation, choose the application name, and on the Configuration Profiles tab, choose Create configuration profile.
7. Enter a name (*MyContainerApplicationConfigurationProfile*) and optional description for the configuration profile.
8. Under Configuration source, choose AWS AppConfig hosted configuration.Under Content, choose JSON, paste the following content, and then choose Next.

```
    {
      "boolEnableFeature": true,
      "intItemLimit": 5
    }
```

9. (Optional) You can add validators to validate the configuration. For information, check [about validators](https://docs.aws.amazon.com/appconfig/latest/userguide/appconfig-creating-configuration-and-profile-validators.html) in the AWS AppConfig documentation.
10. Choose Create configuration profile.
11. Choose Start deployment.
12. Choose the environment, hosted configuration version, deployment strategy, and an optional description to start the deployment process.
13. To create a custom deployment strategy, choose Create Deployment Strategy. Or choose one of the predefined deployment strategies provided by AWS AppConfig. For more information, check [creating a deployment strategy](https://docs.aws.amazon.com/appconfig/latest/userguide/appconfig-creating-deployment-strategy.html) in the AWS AppConfig documentation. For the purposes of this post, we chose the *AppConfig.Linear50PercentEvery30Seconds* deployment strategy.

Note: Depending on the deployment strategy you selected, this operation might take few minutes to complete. The configuration is available to the application as soon as the deployment state is Complete.

### STEP 2: Set up the base application with Amazon ECS and Amazon ECR and associated network components using AWS CloudFormation


1. Open [CloudFormation console](https://console.aws.amazon.com/cloudformation/home) and click on “Create stack”, selecting “With new resources” option.
2. In the next screen, under the “Specify template” section choose “Upload template file“, and provide the file you downloaded from the repo */templates/ecs-cluster.yml.*
3. Click next, give the stack a name like “ECSCluster-dev“, and choose dev as value for the Environment parameter.Click next, optionally define your tags, and click next again. On the last screen, don’t forget to tick the check box in the “Capabilities” section, and finally click on “Create stack” button.
	
### STEP 3: Clone the code repository, create a Docker container, and publish to Amazon ECR

1. Git clone this [Repository](https://github.com/aws-samples/aws-appconfig-java-sample)
2. Navigate to Amazon Elastic Container Registry console , click on the repository that you created and click View push commands.
3. Navigate to the code repository in the command prompt and execute the push commands to upload the project.
4. When the upload is complete, copy the URL of the image in the repository. Use this URL as an input parameter (ImageUrl) to the AWS CloudFormation template mentioned in the next section.

### STEP 4: Create a Fargate task and deploy the container application into Amazon ECS on AWS Fargate using AWS CloudFormation

1. Open [CloudFormation console](https://console.aws.amazon.com/cloudformation/home) and click on “Create stack”, selecting “With new resources” option.
2. In the next screen, under the “Specify template” section choose “Upload template file“, and provide the file you downloaded from the repo */templates/fargate-task.yml.*
3. Click next, give a name to the stack like “fargate-task-dev”. Choose “dev” as value for the Environment parameter.
4. Provide the image URL obtained in the previous step for the ImageUrl parameter and leave the rest of the parameters as default. 
5. Click next and optionally define your tags. Click next again. On the last screen, don’t forget to tick the check box in the “Capabilities” section, and finally click on “Create stack” button.

### STEP 5 : Verify the deployed application, update the AppConfig configuration data, and deploy the updated configuration

1. Navigate to [AWS CloudFormation Console](https://console.aws.amazon.com/cloudformation/home) and open the fargate-task-dev stack you created
2. Click on Outputs and copy the ExternalUrl for the Loadbalancer
3. Verify the application by using the External URL for the Load Balancer. *http://ExternalUrl/movies/getMovies*
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
