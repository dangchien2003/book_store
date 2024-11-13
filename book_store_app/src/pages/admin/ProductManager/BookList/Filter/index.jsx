import { Box, Typography } from '@mui/material'
import Author from './Author'
import Category from './Category'
import Publisher from './Publisher'

const Filter = () => {

  return (
    <Box key='filter' sx={{
      width: '250px', minHeight: '300px', borderRadius: '10px', border: '2px solid #1a89a2', position: 'relative', mt: '10px', padding: '15px', backgroundColor: '', '& .MuiAutocomplete-root': {
        marginTop: '20px'
      }
    }} >
      <Typography variant='span' sx={{
        background: 'white', padding: '3px', fontSize: '18px', color: '#1a89a2', backgroundColor: '#f7fafa', position: 'absolute', top: '-18px', left: '10px'
      }} >Bộ lọc</Typography>
      <Author />
      <Publisher />
      <Category />
    </Box>
  )
}

export default Filter
