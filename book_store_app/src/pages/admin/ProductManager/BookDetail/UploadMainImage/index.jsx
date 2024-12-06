import { Box } from '@mui/material'

import upload from '@/assets/image/upload.png'

const UploadMainImage = () => {

  const handleClickUpload = () => {
    alert('upload main image')
  }

  return (
    <Box sx={{ height: '200px', border: '2px solid black', borderRadius: '10px', width: '200px', display: 'flex', justifyContent: 'center', alignItems: 'center' }} onClick={handleClickUpload}>
      <img src={upload} alt='upload' style={{ maxHeight: '50%' }} />
    </Box>
  )
}

export default UploadMainImage
