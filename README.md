# PROGRAMMING 3D [PROG7314]
## POE Part 2 – README

---
## Youtube video Link:
https://youtu.be/uHv4885ecJA
---
## API URL:
https://petnest-api.onrender.com/
---

## Team members:
- Joshua Ponquett: St10405508
- Thando Phiri: Thando Phiri
- Lihlethando Funde: ST10210396
- Ayana Modise: ST10375530

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
<img width="729" height="410" alt="image" src="https://github.com/user-attachments/assets/70a9720b-df72-4d6f-8461-858bf12b85ac" />

<img width="750" height="398" alt="image" src="https://github.com/user-attachments/assets/9c1e3f9d-cec2-4bd8-b568-57b454971d55" />

<img width="761" height="428" alt="image" src="https://github.com/user-attachments/assets/a88ef7ef-d1df-46e9-8424-fab0a8b9bd94" />

<img width="721" height="383" alt="image" src="https://github.com/user-attachments/assets/30915e7d-df17-4327-a8ed-2e5eb2fb227c" />

<img width="726" height="408" alt="image" src="https://github.com/user-attachments/assets/eede8c74-4680-4f6b-b19f-1a6e5fe829d4" />

<img width="726" height="408" alt="image" src="https://github.com/user-attachments/assets/bd8deb00-d274-4b24-8987-156598ab20c2" />

<img width="731" height="411" alt="image" src="https://github.com/user-attachments/assets/876c2932-5045-494d-8ef6-27399ffc7016" />

<img width="753" height="423" alt="image" src="https://github.com/user-attachments/assets/db534ed4-655d-4480-b292-ea7104d8a906" />

<img width="761" height="404" alt="image" src="https://github.com/user-attachments/assets/e3643286-eae6-4c2e-8602-88e460214687" />

<img width="766" height="431" alt="image" src="https://github.com/user-attachments/assets/a5728c1e-a37b-40c8-afe7-7c0278f74030" />

<img width="675" height="359" alt="image" src="https://github.com/user-attachments/assets/13edaf8f-6a1d-4e65-a22f-1cfabedaaf94" />

<img width="672" height="378" alt="image" src="https://github.com/user-attachments/assets/4dc95fbe-3b7b-4549-b9cc-d98933f8f0b5" />

<img width="686" height="364" alt="image" src="https://github.com/user-attachments/assets/6cf61e98-93b4-448f-90d5-aff13ba60ca8" />

<img width="726" height="408" alt="image" src="https://github.com/user-attachments/assets/d5853953-22de-40f3-8bdb-26ea667aeb8c" />

<img width="726" height="386" alt="image" src="https://github.com/user-attachments/assets/2c6eac7d-a8ed-42a0-b53d-044b2c435274" />

<img width="739" height="416" alt="image" src="https://github.com/user-attachments/assets/bc9f2aab-0118-44a6-87c5-9446d6248174" />

<img width="737" height="391" alt="image" src="https://github.com/user-attachments/assets/b6fe1d9b-4fe4-4552-a55e-4ff9f0f21407" />

<img width="742" height="417" alt="image" src="https://github.com/user-attachments/assets/918d7a4e-f37d-4928-bcf3-581c2789bc5e" />

<img width="721" height="405" alt="image" src="https://github.com/user-attachments/assets/c5c9ea27-34ad-4472-abbf-11ec1c1f7a09" />

<img width="737" height="414" alt="image" src="https://github.com/user-attachments/assets/007de251-1dfd-4897-9b7b-4f293678c7d0" />


<img width="753" height="423" alt="image" src="https://github.com/user-attachments/assets/f6d30120-5472-473d-a5ac-91d929c605d4" />


<img width="716" height="403" alt="image" src="https://github.com/user-attachments/assets/ac713e9a-efd0-4be4-bd9a-4e111e2e7488" />

<img width="726" height="408" alt="image" src="https://github.com/user-attachments/assets/beeeb443-936a-4072-b924-c610be26b9a1" />

<img width="731" height="411" alt="image" src="https://github.com/user-attachments/assets/86934d5c-f9e8-4293-8177-e8346cd97049" />

<img width="836" height="444" alt="image" src="https://github.com/user-attachments/assets/c78b8273-87b0-41c1-b5dc-efad2e6837a0" />

<img width="940" height="529" alt="image" src="https://github.com/user-attachments/assets/5527a158-1b91-4986-8248-b1db50d2a010" />

<img width="940" height="529" alt="image" src="https://github.com/user-attachments/assets/c1c975b7-04af-4142-aa97-e991d22f28d7" />














