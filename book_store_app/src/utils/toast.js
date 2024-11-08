import { toast, Flip } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'

const config = {
  position: 'top-right',
  autoClose: 5000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
  progress: undefined,
  theme: 'colored',
  transition: Flip
}

export function toastSuccess(message) {
  toast.success(message, config)
}

export function toastError(message) {
  toast.error(message, config)
}

export function toastInfo(message) {
  toast.info(message, config)
}

export function toastWarning(message) {
  toast.warning(message, config)
}