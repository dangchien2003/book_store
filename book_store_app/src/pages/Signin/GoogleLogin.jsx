import googleSvg from '@/assets/image/google.svg'
import { Button, Typography } from '@mui/material'
import { generateCodeChallenge, generateCodeVerifier } from '@/utils/pkceUtils'
import React from 'react'
import { googleAuthentication } from '@/services/authService/loginService'
import { useNavigate } from 'react-router-dom'
import { getCodeVerifierToLocalStorage, setCodeVerifierToLocalStorage } from '@/services/localStorageService'
import { clientId, googleAuthUrl, redirectUriForSignIn } from '@/configs/googleAuthConfig'
import { toastError } from '@/utils/toast'
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
  return authorizationCode && codeVerifier
}

const GoogleLogin = () => {
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
        await googleAuthentication(authorizationCode, codeVerifier)
        navigate('/')
      } catch (error) {
        toastError(messageError[error.response.data.code] ?? error.response.data.message)
      } finally {
        cleanUrl()
      }
    }

    authenticateUser()
  }, [navigate])

  const handleGoogleLogin = async () => {
    const scope = 'email profile'
    const codeVerifier = generateCodeVerifier()
    const codeChallenge = await generateCodeChallenge(codeVerifier)
    setCodeVerifierToLocalStorage(codeVerifier)

    const authUrl = `${googleAuthUrl}?response_type=code&redirect_uri=${redirectUriForSignIn}&scope=${scope}&code_challenge=${codeChallenge}&client_id=${clientId}&code_challenge_method=S256`
    window.location.href = authUrl
  }

  return (
    <Button
      variant="outlined"
      onClick={handleGoogleLogin}>
      <img src={googleSvg} alt="google" style={{ height: '25px', paddingRight: '3px' }} />
      <Typography variant='span' sx={{ lineHeight: '25px' }}>Đăng nhập với google </Typography>
    </Button>

  )
}

export default GoogleLogin
