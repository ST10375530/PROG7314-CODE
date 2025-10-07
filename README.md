PROGRAMMING 3D [PROG7314]
POE Part 2 – README

Project Overview

PetNest is a mobile application prototype designed to assist pet owners in managing their pets’ health and wellness. The app provides a centralized platform where users can create pet profiles, track important health information, search for medication details, and receive dosage recommendations based on their pet’s weight and type.

The main goals of PetNest are:
•	To give pet owners a user-friendly tool to manage pet information.
•	To integrate a RESTful API that connects the frontend app with a hosted backend and database.
•	To demonstrate the use of single sign-on (SSO) for secure authentication.
•	To showcase modern app development practices such as external library integration, cloud-hosted APIs, unit testing, and GitHub Actions CI/CD pipelines.

For Part 2 of the PoE, the focus is on developing a working prototype that connects to a hosted backend API and database. The app must also demonstrate SSO login, settings management, and selected features from the Part 1 design document.

API Implementation Summary

The backend for PetNest was implemented as a RESTful API using Node.js and Express.js, with data stored in MongoDB Atlas (a cloud-hosted NoSQL database). The API is hosted on Render, allowing the Android app to connect to it over the internet.

Technologies Used
•	Node.js + Express.js –  framework for building RESTful APIs.
•	MongoDB Atlas –  cloud database to store medicines, dosages, and (future) pet data.
•	Mongoose –  Object Data Modeling (ODM) library for MongoDB.
•	.env –  to manage environment variables securely.
•	Helmet & CORS –  middleware to improve API security.

Core Features Implemented

1.	Medicine Search
o	Allows users to search for medicines by name.
o	Uses regex-based queries for flexible searching.
o	Endpoint: GET /api/medicines/search?q=<term>

2.	Dosage Calculator
o	Calculates correct dosage based on pet weight and type (e.g., dog/cat).
o	Ensures safe dosage recommendations.
o	Endpoint: POST /api/dosage

3.	Medicine Management 
o	GET /api/medicines –  list all medicines.
o	GET /api/medicines/:id –  retrieve medicine by ID.


Features Implemented (Part 2)

For Part 2 of the PoE, the following features were implemented in the PetNest prototype:

•	User Authentication with SSO – 
o	Implemented using Firebase Authentication (Facebok Sign-In).
o	Provides a secure and simple way for users to register and log in without creating a new account.

•	User Settings Management – 
o	Users can update preferences (e.g., notifications, theme mode).
o	Settings are saved per user account.

•	Pet Profiles – 
o	Add pet details: name, type, breed, weight, age, and photo.
o	View and edit pet information.
o	Future enhancements: vaccination history, allergies, vet appointments.

•	Medicine Search – 
o	Search medicines by name using the backend API (/api/medicines/search).
o	Regex-based searching allows partial matches.

•	Error Handling – 
o	Invalid inputs (e.g., empty fields, incorrect weights) handled with clear messages instead of app crashes.
o	Network errors are caught and displayed gracefully.

Deferred for final POE:
•	Sickness identifier (semantic search).
•	Full vet locator using Google Maps SDK.
•	Final design assets and advanced UI polish.

Design Considerations
When designing PetNest, several key considerations guided development:

•	User Experience (UX):
o	Clean navigation using bottom navigation and fragments.
o	Simple, modern layout to make pet data management intuitive.

•	Scalability:
o	Modular API structure in Node.js allows new endpoints/features to be added easily.
o	MongoDB Atlas provides flexible schema for future data like treatments and medical history.

•	Security:
o	Firebase handles secure user authentication.
o	.env file used for sensitive credentials (e.g., MongoDB URI).

•	Reliability:
o	API hosted on Render to ensure internet accessibility.
o	Unit tests and Postman tests validate functionality.

External Libraries & SDKs

•	Android (Frontend):
o	Retrofit –  API communication.
o	Firebase Authentication SDK –  SSO login.
o	Glide –  Image loading.
o	Google Maps SDK (planned) –  Vet locator.

•	Backend (API):
o	Express.js –  REST API framework.
o	Mongoose – ODM for MongoDB.
o	Helmet –  API security.
o	CORS – Handle cross-origin requests.
o	dotenv –  Manage environment variables.

Database & Hosting
•	Database: MongoDB Atlas (cloud cluster).
o	Collections: medicines, users (basic test data).
•	Hosting: Render (Node.js API).
o	Backend accessible through a hosted URL.
•	Authentication Service: Firebase Authentication (Facebook SSO).
•	Deployment Notes: Render free tier spins down after inactivity – can cause first API request delays.

Demo Video
Within GitHub Repository Link: https://github.com/ST10375530/PROG7314-CODE 

Screenshots & Evidence
                           
