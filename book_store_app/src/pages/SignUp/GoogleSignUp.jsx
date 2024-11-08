import googleSvg from '@/assets/image/google.svg'
import { Button, Typography } from '@mui/material'
import { generateCodeChallenge, generateCodeVerifier } from '@/utils/pkceUtils'
import React from 'react'
import { useNavigate } from 'react-router-dom'
import { getCodeVerifierToLocalStorage, setCodeVerifierToLocalStorage } from '@/services/localStorageService'
import { clientId, googleAuthUrl, redirectUriForSignUp } from '@/configs/googleAuthConfig'
import { registerByGoogle } from '@/services/userService'
import { toastError, toastSuccess } from '@/utils/toast'
import { messageError } from '@/configs/messageError'

function getAuthorizationCode() {
  const search = window.location.search
  const params = new URLSearchParams(search)
  return params.get('code')
}

function cleanUrl() {
  const url = window.location.origin + window.location.pathname
  window.history.replaceState({}, document.title, url)
}

function validateCode(authorizationCode, codeVerifier) {
  if (!authorizationCode || !codeVerifier)
    return false
  return true
}

const GoogleSignUp = () => {
  const navigate = useNavigate()
  React.useEffect(() => {
    const authenticateUser = async () => {
      const authorizationCode = getAuthorizationCode()
      const codeVerifier = getCodeVerifierToLocalStorage()
      const codeOk = validateCode(authorizationCode, codeVerifier)

      if (!codeOk) {
        cleanUrl()
        return
      }

      try {
        await registerByGoogle(authorizationCode, codeVerifier)
        toastSuccess('Đăng ký thành công.\nBạn sẽ được đi tới đăng nhập sau 5 giây')
        setTimeout(() => {
          navigate('/auth')
        }, 5000)
      } catch (err) {
        toastError(messageError[err.response.data.code] ?? err.response.data.message)
      } finally {
        cleanUrl()
      }
    }

    authenticateUser()
  }, [navigate])

  const handleGoogleSignUp = async () => {
    const scope = 'email profile'
    const codeVerifier = generateCodeVerifier()
    const codeChallenge = await generateCodeChallenge(codeVerifier)
    setCodeVerifierToLocalStorage(codeVerifier)

    const authUrl = `${googleAuthUrl}?response_type=code&redirect_uri=${redirectUriForSignUp}&scope=${scope}&code_challenge=${codeChallenge}&client_id=${clientId}&code_challenge_method=S256`
    window.location.href = authUrl
  }

  return (
    <Button
      variant="outlined"
      onClick={handleGoogleSignUp}>
      <img src={googleSvg} alt="google" style={{ height: '25px', paddingRight: '3px' }} />
      <Typography variant='span' sx={{ lineHeight: '25px' }}>Đăng ký với google </Typography>
    </Button>
  )
}

export default GoogleSignUp
