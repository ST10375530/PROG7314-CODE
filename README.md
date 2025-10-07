# PROGRAMMING 3D [PROG7314]
## POE Part 2 – README

---
##Youtube video Link:
https://youtu.be/uHv4885ecJA
---
## API URL:
https://petnest-api.onrender.com/
---
## Project Overview

**PetNest** is a mobile application prototype designed to assist pet owners in managing their pets’ health and wellness.  
The app provides a centralized platform where users can:

- Create pet profiles  
- Track important health information  
- Search for medication details  
- Receive dosage recommendations based on their pet’s weight and type  

### Main Goals of PetNest

- Provide pet owners a user-friendly tool to manage pet information  
- Integrate a RESTful API that connects the frontend app with a hosted backend and database  
- Demonstrate single sign-on (SSO) for secure authentication  
- Showcase modern app development practices such as:
  - External library integration  
  - Cloud-hosted APIs  
  - Unit testing  
  - GitHub Actions CI/CD pipelines  

For **Part 2** of the PoE, the focus is on developing a working prototype that connects to a hosted backend API and database.  
The app must also demonstrate **SSO login**, **settings management**, and selected features from the Part 1 design document.

---

## API Implementation Summary

The backend for PetNest was implemented as a **RESTful API** using **Node.js** and **Express.js**,  
with data stored in **MongoDB Atlas** (a cloud-hosted NoSQL database).  
The API is hosted on **Render**, allowing the Android app to connect to it over the internet.

### Technologies Used

- **Node.js + Express.js** – Framework for building RESTful APIs  
- **MongoDB Atlas** – Cloud database to store medicines, dosages, and (future) pet data  
- **Mongoose** – Object Data Modeling (ODM) library for MongoDB  
- **dotenv (.env)** – Manage environment variables securely  
- **Helmet & CORS** – Middleware to improve API security  

---

## Core Features Implemented

### Medicine Search
- Allows users to search for medicines by name  
- Uses regex-based queries for flexible searching  
- **Endpoint:** `GET /api/medicines/search?q=<term>`

### Dosage Calculator
- Calculates correct dosage based on pet weight and type (e.g., dog/cat)  
- Ensures safe dosage recommendations  
- **Endpoint:** `POST /api/dosage`

### Medicine Management
- `GET /api/medicines` – List all medicines  
- `GET /api/medicines/:id` – Retrieve medicine by ID  

---

## Features Implemented (Part 2)

### User Authentication with SSO
- Implemented using **Firebase Authentication (Facebook Sign-In)**  
- Provides a secure and simple way for users to register and log in without creating a new account  

### User Settings Management
- Users can update preferences (e.g., notifications, theme mode)  
- Settings are saved per user account  

### Pet Profiles
- Add pet details: name, type, breed, weight, age, and photo  
- View and edit pet information  
- **Future Enhancements:** vaccination history, allergies, vet appointments  

### Medicine Search
- Search medicines by name using the backend API (`/api/medicines/search`)  
- Regex-based searching allows partial matches  

### Error Handling
- Invalid inputs (e.g., empty fields, incorrect weights) handled with clear messages instead of crashes  
- Network errors are caught and displayed gracefully  

### Deferred for Final POE
- Sickness identifier (semantic search)  
- Full vet locator using Google Maps SDK  
- Final design assets and advanced UI polish  

---

## Design Considerations

### User Experience (UX)
- Clean navigation using bottom navigation and fragments  
- Simple, modern layout to make pet data management intuitive  

### Scalability
- Modular API structure in Node.js allows new endpoints/features to be added easily  
- MongoDB Atlas provides flexible schema for future data like treatments and medical history  

### Security
- Firebase handles secure user authentication  
- `.env` file used for sensitive credentials (e.g., MongoDB URI)  

### Reliability
- API hosted on Render to ensure internet accessibility  
- Unit tests and Postman tests validate functionality  

---

## External Libraries & SDKs

### Android (Frontend)
- **Retrofit** – API communication  
- **Firebase Authentication SDK** – SSO login  
- **Glide** – Image loading  
- **Google Maps SDK (planned)** – Vet locator  

### Backend (API)
- **Express.js** – REST API framework  
- **Mongoose** – ODM for MongoDB  
- **Helmet** – API security  
- **CORS** – Handle cross-origin requests  
- **dotenv** – Manage environment variables  

---

## Database & Hosting

- **Database:** MongoDB Atlas (cloud cluster)  
- **Collections:** medicines, users (basic test data)  
- **Hosting:** Render (Node.js API)  
- **Backend Access:** Hosted URL via Render  
- **Authentication Service:** Firebase Authentication (Facebook SSO)  

> **Note:** Render’s free tier spins down after inactivity, causing delays on the first API request.

---

## Demo Video

📺 **GitHub Repository:**  
[https://github.com/ST10375530/PROG7314-CODE](https://github.com/ST10375530/PROG7314-CODE)

---

## Screenshots & Evidence

*(Screenshots and additional supporting materials to be added here.)*
