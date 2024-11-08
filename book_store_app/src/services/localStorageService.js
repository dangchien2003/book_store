const googleCodeVerifierkey = 'googleCodeVerifier'
const refeshTokenkey = 'refresh'

export function setCodeVerifierToLocalStorage(value) {
  localStorage.setItem(googleCodeVerifierkey, value)
}

export function getCodeVerifierToLocalStorage() {
  return localStorage.getItem(googleCodeVerifierkey)
}

export function setRefeshToken(token) {
  localStorage.setItem(refeshTokenkey, token)
}

export function getRefeshToken() {
  return localStorage.getItem(refeshTokenkey)
}
