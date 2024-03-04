export const useMyFetch = (request, opts) => {
    const config = useRuntimeConfig()
  
    return useLazyFetch(request, { baseURL: config.public.baseURL, server: false, lazy:false, ...opts })
  }