# Use the official Node.js 14 image as a parent image
FROM node:14

# Set the working directory inside the container to /app
WORKDIR /app

# Copy package.json and package-lock.json to /app
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files to /app
COPY ../backend/src/main/webapp .

# Build the Angular application for production
RUN npm run build

# Expose port 80
EXPOSE 80

# Start the server
CMD ["npm", "run", "start"]
