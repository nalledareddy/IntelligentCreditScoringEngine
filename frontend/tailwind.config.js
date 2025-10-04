/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        primary: '#0ea5a4',
        darkbg: '#0b1220',
        card: '#0f1724'
      }
    },
  },
  plugins: [],
}
