import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientDocument, defaultValue } from 'app/shared/model/client-document.model';

export const ACTION_TYPES = {
  FETCH_CLIENTDOCUMENT_LIST: 'clientDocument/FETCH_CLIENTDOCUMENT_LIST',
  FETCH_CLIENTDOCUMENT: 'clientDocument/FETCH_CLIENTDOCUMENT',
  CREATE_CLIENTDOCUMENT: 'clientDocument/CREATE_CLIENTDOCUMENT',
  UPDATE_CLIENTDOCUMENT: 'clientDocument/UPDATE_CLIENTDOCUMENT',
  DELETE_CLIENTDOCUMENT: 'clientDocument/DELETE_CLIENTDOCUMENT',
  SET_BLOB: 'clientDocument/SET_BLOB',
  RESET: 'clientDocument/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientDocument>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ClientDocumentState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientDocumentState = initialState, action): ClientDocumentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDOCUMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTDOCUMENT):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTDOCUMENT):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDOCUMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDOCUMENT):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTDOCUMENT):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTDOCUMENT):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTDOCUMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDOCUMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDOCUMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTDOCUMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/client-documents';

// Actions

export const getEntities: ICrudGetAllAction<IClientDocument> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDOCUMENT_LIST,
    payload: axios.get<IClientDocument>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IClientDocument> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDOCUMENT,
    payload: axios.get<IClientDocument>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClientDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTDOCUMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTDOCUMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientDocument> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTDOCUMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
