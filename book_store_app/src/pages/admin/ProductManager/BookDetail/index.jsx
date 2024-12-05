import { Box, Grid } from '@mui/material'
// import { useParams } from 'react-router-dom'
import UploadMainImage from './UploadMainImage'
import UploadChildImage from './UploadChilden'
import Detail from './Detail'

const BookDetail = () => {
  // const { id } = useParams()

  return (
    <Grid container >
      <Grid item sm={12} md={4} sx={{ display: 'flex', justifyContent: 'center', padding: '10px' }}>
        <Box sx={{ width: '100%' }}>
          <UploadMainImage />
          <UploadChildImage />
        </Box>
      </Grid>
      <Grid item sm={12} md={8}>
        <Detail />
      </Grid>
    </Grid>
  )
}

export default BookDetail
