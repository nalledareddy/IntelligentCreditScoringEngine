import React from 'react'
import RegisterForm from './components/RegisterForm'

export default function App() {
  return (
    <div className="min-h-screen flex items-center justify-center p-6">
      <div className="w-full max-w-4xl grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="card">
          <h1 className="text-3xl font-semibold mb-2 text-gray-100">Intelligent Credit Scoring</h1>
          <p className="text-sm text-gray-300 mb-6">Enter applicant details to get a credit score powered by an ML model.</p>
          <RegisterForm />
        </div>

        <div className="card">
          <h2 className="text-2xl font-semibold mb-4">Recent Applicants</h2>
          <div id="list-placeholder" />
          <p className="mt-6 text-sm text-gray-400">This pane shows applicants saved in backend and their computed scores.</p>
        </div>
      </div>
    </div>
  )
}
