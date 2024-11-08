import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import { Button, Stack } from '@mui/material'
import PasswordField from './PasswordField'
import Remember from './Remember'
import GoogleLogin from './GoogleLogin'
import { useState } from 'react'
import { normalAuthentication } from '@/services/authService/loginService'
import { deleteRememberUsername, getRememberUsername, setAccessToken, setRememberUsername } from '@/services/cookieService'
import { setRefeshToken } from '@/services/localStorageService'
import { useNavigate } from 'react-router-dom'
import { messageError } from '@/configs/messageError'
import { ToastContainer } from 'react-toastify'
import { toastError } from '@/utils/toast'

const SignInForm = () => {
  const [username, setUsername] = useState(getRememberUsername())
  const [password, setPassword] = useState('')
  const [remember, setRemember] = useState(!!username)
  const navigate = useNavigate()

  const handleChangeUsername = (event) => {
    setUsername(event.target.value)
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value)
  }

  const handleClickLogin = () => {
    normalAuthentication(username, password).then((response) => {

      setAccessToken(response.data.result.accessToken, response.data.result.expire)
      setRefeshToken(response.data.result.refreshToken)

      if (remember)
        setRememberUsername(username)
      else
        deleteRememberUsername()

      if (response.data.result.actor === 'ADMIN')
        navigate('/manager')
      else
        navigate('/')
    }).catch((error) => {
      toastError(messageError[error.response.data.code] ?? error.response.data.message)
    })
  }

  const handleChangeRemember = (checked) => {
    setRemember(checked)
  }


  return (
    <Box pt={2}>
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
