import org.opencv.core.*;

//这段代码，只是用来测试opencv是否可用
public class Hello {



        public static void main( String[] args )
        {
            System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
            Mat m = Mat.eye( 2, 3, CvType.CV_8UC1 );
            System.out.println(m.dump());
        }

}
