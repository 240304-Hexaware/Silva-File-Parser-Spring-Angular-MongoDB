import { HttpContext, HttpHeaders, HttpParams } from '@angular/common/http';

export interface Options {
  headers?:
    | HttpHeaders
    | {
        [header: string]: string | string[];
      };
  observe?: 'body';
  context?: HttpContext;
  params?:
    | HttpParams
    | {
        [param: string]:
          | string
          | number
          | boolean
          | ReadonlyArray<string | number | boolean>;
      };
  reportProgress?: boolean;
  responseType?: any;
  withCredentials?: boolean;
  transferCache?:
    | {
        includeHeaders?: string[];
      }
    | boolean;
}

// TODO: Get rid of password
export interface User {
  id?: any; //Find a type for objectID
  username: string;
  password: string;
  listOfSpecFileIds?: any[];
  listOfParsedFileIds?: any[];
}

export interface ParsedFile {
  id?: { date: string; timestamp: number };
  userId: { date: string; timestamp: number };
  specId: { date: string; timestamp: number };
  metaDataId: { date: string; timestamp: number };
  fileInfo: {};
}

export interface SpecFile {
  id?: { date: string; timestamp: number };
  createdUserId: { date: string; timestamp: number };
  name: string;
  docOfFields: {};
}

// Helpful Interfaces For Events
export interface EventFlatAndSpec {
  file: File;
  specFile: SpecFile;
}
