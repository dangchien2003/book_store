import { Box } from '@mui/material'
import buttonUpload from '@/assets/image/plus.png'

const UploadChildImage = () => {

  const handleClickUpload = () => {
    alert('upload child image')
  }

  return (
    <Box sx={{ display: 'flex', marginTop: '15px', flexWrap: 'wrap', gap: '5px' }}>
      <img src={buttonUpload} style={{ width: '50px', height: '50px' }} onClick={handleClickUpload} />
    </Box>
  )
}

export default UploadChildImage
