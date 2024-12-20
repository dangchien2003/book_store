import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import { Button, Stack } from '@mui/material'
import PasswordField from './PasswordField'
import Remember from './Remember'
import GoogleLogin from './GoogleLogin'
import { useCallback, useEffect, useState } from 'react'
import { normalAuthentication } from '@/services/authService/loginService'
import { deleteRememberUsername, getRememberUsername, setRememberUsername } from '@/services/cookieService'
import { setAccessToken, setRefeshToken } from '@/services/localStorageService'
import { useNavigate } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'


const SignInForm = () => {
  const [username, setUsername] = useState(getRememberUsername())
  const [password, setPassword] = useState('')
  const [remember, setRemember] = useState(!!username)
  const [enterClicked, setEnterClicked] = useState(false)
  const navigate = useNavigate()

  const handleChangeUsername = (event) => {
    setUsername(event.target.value)
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value)

  }

  const handleClickLogin = useCallback(() => {
    normalAuthentication(username, password).then((response) => {
      setAccessToken(response.data.result.accessToken, response.data.result.expire)
      setRefeshToken(response.data.result.refreshToken)

      if (remember)
        setRememberUsername(username)
      else
        deleteRememberUsername()

      if (response.data.result.manager)
        navigate('/manager')
      else
        navigate('/store/home')
    }).catch(() => {
      // const response = error.response.data
      // if (response.code === 1003) {
      //   const fieldBlank = response.message.split('Field not blank: ')
      //   console.log(field)
      // }
    }).finally(() => {
      setTimeout(() => {
        setEnterClicked(false)
      }, 3000)
    })
  })

  const handleChangeRemember = (checked) => {
    setRemember(checked)
  }

  useEffect(() => {
    const handleKeyPress = (e) => {
      if (enterClicked) return

      if (e.key === 'Enter') {
        e.preventDefault()
        setEnterClicked(true)
        handleClickLogin()
      }
    }

    document.addEventListener('keypress', handleKeyPress)

    return () => {
      document.removeEventListener('keypress', handleKeyPress)
    }
  }, [handleClickLogin, enterClicked])

  return (
    <Box pt={2} >
      <ToastContainer />
      <Stack gap={1} >
        <TextField
          id="standard-basic"
          label="Tài khoản"
          variant="standard"
          sx={{ width: '100%', marginBottom: 1 }}
          onChange={handleChangeUsername}
          value={username}
        />
        <PasswordField value={password} onPasswordChange={handleChangePassword} />
        <Remember onCheck={handleChangeRemember} value={remember} />
        <Button variant="contained" onClick={handleClickLogin}>Đăng nhập</Button>
        <Stack direction='row' justifyContent='center'>
          <GoogleLogin />
        </Stack>
      </Stack>
    </Box>
  )
}

export default SignInForm
