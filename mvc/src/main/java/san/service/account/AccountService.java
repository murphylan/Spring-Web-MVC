package san.service.account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
	
	public static  void createFile(InputStream io, String fileName) throws IOException {
        FileUtils.forceMkdir(new File(fileName));
    	OutputStream output=new FileOutputStream(new File(fileName));
    	org.apache.commons.io.IOUtils.copy(io, output);
    }
}
