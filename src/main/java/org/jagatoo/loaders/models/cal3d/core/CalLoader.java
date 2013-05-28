/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
package org.jagatoo.loaders.models.cal3d.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;

import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/*****************************************************************************/
/** Provides static methods for loading Cal3d elements from backing store.
 *****************************************************************************/
public class CalLoader {
    /** File format magic headers **/
    public static final byte [ ] SKELETON_FILE_MAGIC  = { 'C', 'S', 'F', '\0' };
    private static final String SKELETON_XMLFILE_EXTENSION = ".xsf";
    @SuppressWarnings("unused")
    private static final String SKELETON_XMLFILE_MAGIC = "XSF";
    
    public static final byte [ ] ANIMATION_FILE_MAGIC = { 'C', 'A', 'F', '\0' };
    private static final String ANIMATION_XMLFILE_EXTENSION = ".xaf";
    @SuppressWarnings("unused")
    private static final String ANIMATION_XMLFILE_MAGIC = "XAF";
    
    public static final byte [ ] MESH_FILE_MAGIC      = { 'C', 'M', 'F', '\0' };
    private static final String MESH_XMLFILE_EXTENSION = ".xmf";
    private static final String MESH_XMLFILE_MAGIC = "XMF";
    
    public static final byte [ ] MATERIAL_FILE_MAGIC  = { 'C', 'R', 'F', '\0' };
    private static final String MATERIAL_XMLFILE_EXTENSION = ".xrf";
    @SuppressWarnings("unused")
    private static final String MATERIAL_XMLFILE_MAGIC = "XRF";
    
    /** Library version number */
    public static final int LIBRARY_VERSION = 1000;
    
    /** File version number */
    public static final int CURRENT_FILE_VERSION = LIBRARY_VERSION;
    public static final int EARLIEST_COMPATIBLE_FILE_VERSION = 699;
    
    /** Flag for the loader to rotate X to Y axis. */
    public static final int LOADER_ROTATE_X_AXIS = 1;
    
    /** Flags used by the loader - note this is static so all loads must be consistent.
     */
    protected static int loadingMode;
    
    public static void setLoadingMode(int flags) {
        loadingMode = flags;
    }
    
    /*****************************************************************************/
    /** Loads a core animation instance.
     *
     * This function loads a core animation instance from a file.
     *
     * @param resource The name of the file to load the core animation instance
     *                    from.
     * @param name 
     *
     * @return One of the following values:
     *         \li a pointer to the core animation
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public static CalCoreAnimation loadCoreAnimation(URL resource, String name) throws IOException {
        
        InputStream in = resource.openStream();
        
        if (resource.toExternalForm().toLowerCase().endsWith(ANIMATION_XMLFILE_EXTENSION)) {
            return loadXmlCoreAnimation(in);
        }
        
        try {
            return loadCoreAnimation(in, name);
        } finally {
            in.close();
        }
    }

    /*****************************************************************************/
    /** Loads a core animation instance from a Xml.
     *
     * This function loads a core animation instance from a Xml file.
     *
     * @param in The name of the Xml file to load the core animation instance
     *                    from.
     *
     * @return One of the following values:
     *         \li a pointer to the core animation
     *         \li \b 0 if an error happend
     *****************************************************************************/
    private static CalCoreAnimation loadXmlCoreAnimation(InputStream in) {
        
        // TODO : Fill in ^^
        
        throw new UnsupportedOperationException("The XML file format isn't supported yet by Cal3Dj." +
                "\nPlease use the binary format (.CMF, .CAF, .CSF, .CRF) instead, or implement XML" +
                "\nXML support yourself.");
        
        //return null;
        
    }

    public static CalCoreAnimation loadCoreAnimation(InputStream in, String name) throws IOException {
        // open the file
        LittleEndianDataInputStream file = new LittleEndianDataInputStream(in);
        
        // check if this is a valid file
        byte [ ] magic = new byte [4];
        file.readFully(magic);
        if (!new String(magic).equals(new String(ANIMATION_FILE_MAGIC)))
            throw new IllegalArgumentException("File not a cal3d animation file (CAF)");
        
        // check if the version is compatible with the library
        int version = file.readInt();
        if((version < EARLIEST_COMPATIBLE_FILE_VERSION) || (version > CURRENT_FILE_VERSION))
            throw new IllegalArgumentException("Incompatible cal3d file version");
        
        // allocate a new core animation instance
        CalCoreAnimation coreAnimation;
        coreAnimation = new CalCoreAnimation();
        
        // get the duration of the core animation
        float duration = file.readFloat();
        
        // check for a valid duration
        if(duration <= 0.0f)
            throw new IOException("Invalid animation duration in cal3d animation file");
        
        // set the duration in the core animation instance
        coreAnimation.setDuration(duration);
        
        // read the number of tracks
        int trackCount = file.readInt();
        if((trackCount <= 0))
            throw new IOException("Invalid track count in cal3d animation file");
        
        // load all core tracks
        for(int trackId = 0; trackId < trackCount; trackId++) {
            // load the core track
            CalCoreTrack coreTrack = loadCoreTrack(file);
            
            // add the core track to the core animation instance
            coreAnimation.addCoreTrack(coreTrack);
        }
        
        // set name
        coreAnimation.setName(name);
        
        return coreAnimation;
    }
    
    
    /*****************************************************************************/
    /** Loads a core bone instance.
     *
     * This function loads a core bone instance from a file stream.
     *
     * @param file The file stream to load the core bone instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core bone
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    protected static CalCoreBone loadCoreBones(DataInput file) throws IOException {
        // read the name of the bone - trim extraneous spaces from fixed length formats
        String strName = CalPlatform.readString(file).trim();
        
        // get the translation of the bone
        float tx, ty, tz;
        tx = file.readFloat();
        ty = file.readFloat();
        tz = file.readFloat();
        
        // get the rotation of the bone
        float rx, ry, rz, rw;
        rx = file.readFloat();
        ry = file.readFloat();
        rz = file.readFloat();
        rw = file.readFloat();
        
        // get the bone space translation of the bone
        float txBoneSpace, tyBoneSpace, tzBoneSpace;
        txBoneSpace = file.readFloat();
        tyBoneSpace = file.readFloat();
        tzBoneSpace = file.readFloat();
        
        // get the bone space rotation of the bone
        float rxBoneSpace, ryBoneSpace, rzBoneSpace, rwBoneSpace;
        rxBoneSpace = file.readFloat();
        ryBoneSpace = file.readFloat();
        rzBoneSpace = file.readFloat();
        rwBoneSpace = file.readFloat();
        
        // get the parent bone id
        int parentId = file.readInt();
        
        // allocate a new core bone instance
        CalCoreBone coreBone;
        coreBone = new CalCoreBone(strName);
        
        // set the parent of the bone
        coreBone.setParentId(parentId);
        
        // set all attributes of the bone
        coreBone.setTranslation(new Vector3f(tx, ty, tz));
        coreBone.setRotation(new Quaternion4f(rx, ry, rz, rw));
        coreBone.setTranslationBoneSpace(new Vector3f(txBoneSpace, tyBoneSpace, tzBoneSpace));
        coreBone.setRotationBoneSpace(new Quaternion4f(rxBoneSpace, ryBoneSpace, rzBoneSpace, rwBoneSpace));
        
        // read the number of children
        int childCount = file.readInt();
        if((childCount < 0)) {
            throw new IOException("Invalid child count in cal3d skeleton file");
        }
        
        // load all children ids
        for(; childCount > 0; childCount--) {
            int childId = file.readInt();
            if((childId < 0)) {
                throw new IOException("Invalid child id in cal3d skeleton file");
            }
            
            coreBone.addChildId(childId);
        }
        
        return coreBone;
    }
    
    
    /*****************************************************************************/
    /** Loads a core keyframe instance.
     *
     * This function loads a core keyframe instance from a file stream.
     *
     * @param file The file stream to load the core keyframe instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core keyframe
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    protected static CalCoreKeyframe loadCoreKeyframe(DataInput file) throws IOException {
        // get the time of the keyframe
        float time = file.readFloat();
        
        // get the translation of the bone
        float tx, ty, tz;
        tx = file.readFloat();
        ty = file.readFloat();
        tz = file.readFloat();
        
        // get the rotation of the bone
        float rx, ry, rz, rw;
        rx = file.readFloat();
        ry = file.readFloat();
        rz = file.readFloat();
        rw = file.readFloat();
        
        // allocate a new core keyframe instance
        CalCoreKeyframe coreKeyframe = new CalCoreKeyframe();
        
        // set all attributes of the keyframe
        coreKeyframe.setTime(time);
        coreKeyframe.setTranslation(new Vector3f(tx, ty, tz));
        coreKeyframe.setRotation(new Quaternion4f(rx, ry, rz, rw));
        
        return coreKeyframe;
    }
    
    
    /*****************************************************************************/
    /** Loads a core material instance.
     *
     * This function loads a core material instance from a file.
     *
     * @param resource The name of the file to load the core material instance
     *                    from.
     *
     * @return One of the following values:
     *         \li a pointer to the core material
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public static CalCoreMaterial loadCoreMaterial(URL resource) throws IOException {
        
        InputStream in = resource.openStream();
        
        if (resource.toExternalForm().toLowerCase().endsWith(MATERIAL_XMLFILE_EXTENSION)) {
            return loadXmlCoreMaterial(in);
        }
        
        try {
            return loadCoreMaterial(in);
        } finally {
            in.close();
        }
    }
    
    /*****************************************************************************/
    /** Loads a core material instance from a Xml file.
     *
     * This function loads a core material instance from a Xml file.
     *
     * @param in The name of the file to load the core material instance
     *                    from.
     *
     * @return One of the following values:
     *         \li a pointer to the core material
     *         \li \b 0 if an error happend
     *****************************************************************************/
    private static CalCoreMaterial loadXmlCoreMaterial(InputStream in) {
        
        //TODO : Fill in ^^
        
        throw new UnsupportedOperationException("The XML file format isn't supported yet by Cal3Dj." +
                "\nPlease use the binary format (.CMF, .CAF, .CSF, .CRF) instead, or implement XML" +
                "\nXML support yourself.");
        
        //return null;
        
    }

    public static CalCoreMaterial loadCoreMaterial(InputStream in) throws IOException {
        // open the file
        LittleEndianDataInputStream file = new LittleEndianDataInputStream(in);
        
        // check if this is a valid file
        byte [ ] magic = new byte [4];
        file.readFully(magic);
        if (!new String(magic).equals(new String(MATERIAL_FILE_MAGIC)))
            throw new IllegalArgumentException("File not a cal3d material file (CRF)");
        
        // check if the version is compatible with the library
        int version = file.readInt();
        if((version < EARLIEST_COMPATIBLE_FILE_VERSION) || (version > CURRENT_FILE_VERSION))
            throw new IllegalArgumentException("Incompatible cal3d file version");
        
        
        // allocate a new core material instance
        CalCoreMaterial coreMaterial = new CalCoreMaterial();
        
        // get the ambient color of the core material
        Colorf ambientColor = CalPlatform.readColour(file);
        
        // get the diffuse color of the core material
        Colorf diffuseColor = CalPlatform.readColour(file);
        
        // get the specular color of the core material
        Colorf specularColor = CalPlatform.readColour(file);
        
        // get the shininess factor of the core material
        float shininess = file.readFloat();
        
        // set the colors and the shininess
        coreMaterial.setAmbientColor(ambientColor);
        coreMaterial.setDiffuseColor(diffuseColor);
        coreMaterial.setSpecularColor(specularColor);
        coreMaterial.setShininess(shininess);
        
        // read the number of maps
        int mapCount = file.readInt();
        if((mapCount < 0))
            throw new IOException("Invalid map count in cal3d material file");
        
        // reserve memory for all the material data
        coreMaterial.reserve(mapCount);
        
        // load all maps
        for(int mapId = 0; mapId < mapCount; mapId++) {
            CalCoreMaterial.Map map = new CalCoreMaterial.Map();
            
            // read the filename of the map
            map.filename = CalPlatform.readString(file);
            
            // initialize the user data
            map.userData = null;
            
            // set map in the core material instance
            coreMaterial.setMap(mapId, map);
        }
        
        return coreMaterial;
    }
    
    
    /*****************************************************************************/
    /** Loads a core mesh instance.
     *
     * This function loads a core mesh instance from a file.
     *
     * @param resource The name of the file to load the core mesh instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core mesh
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public static CalCoreMesh loadCoreMesh(URL resource) throws IOException {
        
        InputStream in = resource.openStream();
        
        if (resource.toExternalForm().toLowerCase().endsWith(MESH_XMLFILE_EXTENSION)) {
            return loadXmlCoreMesh(in);
        }
        
        try {
            return loadCoreMesh(in);
        } finally {
            in.close();
        }
    }
    
    public static CalCoreMesh loadCoreMesh(InputStream in) throws IOException {
        
        if (!(in instanceof BufferedInputStream))
            in = new BufferedInputStream(in);
        
        // open the file
        LittleEndianDataInputStream file = new LittleEndianDataInputStream(in);
        
        // check if this is a valid file
        byte [ ] magic = new byte [4];
        file.readFully(magic);
        if (!new String(magic).equals(new String(MESH_FILE_MAGIC)))
            throw new IllegalArgumentException("File not a cal3d mesh file (CMF)");
        
        // check if the version is compatible with the library
        int version = file.readInt();
        if((version < EARLIEST_COMPATIBLE_FILE_VERSION) || (version > CURRENT_FILE_VERSION))
            throw new IllegalArgumentException("Incompatible cal3d file version");
        
        // get the number of submeshes
        int submeshCount = file.readInt();
        
        // allocate a new core mesh instance
        CalCoreMesh coreMesh = new CalCoreMesh();
        
        // load all core submeshes
        for(int submeshId = 0; submeshId < submeshCount; submeshId++) {
            // load the core submesh
            CalCoreSubmesh coreSubmesh = loadCoreSubmesh(file);
            
            // add the core submesh to the core mesh instance
            coreMesh.addCoreSubmesh(coreSubmesh);
        }
        
        return coreMesh;
    }
    
    
    /*****************************************************************************/
    /** Loads a core skeleton instance.
     *
     * This function loads a core skeleton instance from a file.
     *
     * @param resource The name of the file to load the core skeleton instance
     *                    from.
     *
     * @return One of the following values:
     *         \li a pointer to the core skeleton
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public static CalCoreSkeleton loadCoreSkeleton(URL resource) throws IOException {

        InputStream in = resource.openStream();
        
        if (resource.toExternalForm().toLowerCase().endsWith(SKELETON_XMLFILE_EXTENSION)) {
            return loadXmlCoreSkeleton(in);
        }

        try {
            return loadCoreSkeleton(in);
        } finally {
            in.close();
        }
    }
    
    /**
     * Loads a core skeleton instance from a Xml file.
     *
     * This function loads a core skeleton instance from a Xml file.
     *
     * @param in The name of the file to load the core skeleton instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core skeleton
     *         \li \b 0 if an error happened
     */
    private static CalCoreSkeleton loadXmlCoreSkeleton(InputStream in) {
        
        //TODO : Fill in ^^
        
        throw new UnsupportedOperationException("The XML file format isn't supported yet by Cal3Dj." +
                "\nPlease use the binary format (.CMF, .CAF, .CSF, .CRF) instead, or implement XML" +
                "\nXML support yourself.");
        
        //return null;
        
    }

    /**
      * Loads a core mesh instance from a Xml file.
      *
      * This function loads a core mesh instance from a Xml file.
      *
      * @param in The name of the file to load the core mesh instance from.
      *
      * @return One of the following values:
      *         \li a pointer to the core mesh
      *         \li \b 0 if an error happened
      */

    private static CalCoreMesh loadXmlCoreMesh(InputStream in)
    {
        //TODO : Finish
        
        Document doc;
        
        SAXBuilder sb = new SAXBuilder();
        BufferedReader bR;
        
        // Read
        try {
            bR = getCleanXML(in, MESH_XMLFILE_MAGIC);
            doc = sb.build(bR);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Element mesh = doc.getRootElement();
        
        // get the number of submeshes
        @SuppressWarnings("unused")
        int submeshCount = toInt(mesh.getAttributeValue("NUMSUBMESH"));
        
        // allocate a new core mesh instance
        @SuppressWarnings("unused")
        CalCoreMesh pCoreMesh = new CalCoreMesh();
        
        // load all core submeshes
        List<?> subMeshes = mesh.getChildren("SUBMESH");
        for(Object o : subMeshes) {
            
            Element subMesh = (Element) o;
            
            // get the material thread id of the submesh
            int coreMaterialThreadId = toInt(subMesh.getAttribute("MATERIAL").getValue());

            // get the number of vertices, faces, level-of-details and springs
            int vertexCount = toInt(subMesh.getAttribute("NUMVERTICES").getValue());

            int faceCount = toInt(subMesh.getAttribute("NUMFACES").getValue());

            int lodCount = toInt(subMesh.getAttribute("NUMLODSTEPS").getValue());

            int springCount = toInt(subMesh.getAttribute("NUMSPRINGS").getValue());

            int textureCoordinateCount = toInt(subMesh.getAttribute("NUMTEXCOORDS").getValue());

            // allocate a new core submesh instance
            CalCoreSubmesh pCoreSubmesh = new CalCoreSubmesh();
            
            // set the LOD step count
            pCoreSubmesh.setLodCount(lodCount);

            // set the core material id
            pCoreSubmesh.setCoreMaterialThreadId(coreMaterialThreadId);

            // reserve memory for all the submesh data
            pCoreSubmesh.reserve(vertexCount, textureCoordinateCount, faceCount, springCount);
            
            List<?> vertices = subMesh.getChildren("VERTEX");
            
            int vertexId = 0;
            
            for(Object o2 : vertices) {
                
                Element vertex = (Element) o2;
                
                Vector3f position = new Vector3f();
                read(vertex.getChild("POS"), position);
                Vector3f normal = new Vector3f();
                read(vertex.getChild("NORM"), normal);
                
                CalCoreSubmesh.VertexInfo vertexInfo = new CalCoreSubmesh.VertexInfo();
                Element collapseId = vertex.getChild("COLLAPSEID");
                if(collapseId != null) {
                    vertexInfo.collapseId = toInt(collapseId.getText());
                } else {
                    vertexInfo.collapseId = -1;
                }
                
                Element collapseCount = vertex.getChild("COLLAPSECOUNT");
                if(collapseCount != null) {
                    vertexInfo.faceCollapseCount = toInt(collapseCount.getText());
                } else {
                    vertexInfo.faceCollapseCount = 0;
                }
                
                pCoreSubmesh.setVertex(vertexId, vertexInfo, position, normal);
                
                // load all texture coordinates of the vertex
                List<?> texCoords = vertex.getChildren("TEXCOORD");
                
                int texCoordId = 0;
                
                for(Object o3 : texCoords) {
                    
                    Element texCoord = (Element) o3;
                    
                    Vector2f texCoord2f = new Vector2f();
                    
                    StringTokenizer token = new StringTokenizer(texCoord.getValue(), " "); 
                    texCoord2f.setX( toFloat(token.nextToken()) );
                    texCoord2f.setY( 1.0f - toFloat(token.nextToken()) );

                    // set texture coordinate in the core submesh instance
                    pCoreSubmesh.setTextureCoordinate(vertexId, texCoordId++, texCoord2f);

                }

                // get the number of influences
                int influenceCount= toInt(vertex.getAttribute("NUMINFLUENCES").getValue());
                
                // reserve memory for the influences in the vertex
                vertexInfo.influenceBoneIds = new int[influenceCount];
                vertexInfo.influenceWeights = new float[influenceCount];
                
                List<?> influences = vertex.getChildren("INFLUENCE");
                
                int influenceId = 0;
                
                for(Object o3 : influences) {
                    
                    Element influence = (Element) o3;
                    
                    vertexInfo.influenceBoneIds[influenceId] = toInt(influence.getAttribute("ID").getValue());
                    vertexInfo.influenceWeights[influenceId] = toFloat(influence.getValue());
                    
                    influenceId++;
                    
                }
                
                // load the physical property of the vertex if there are springs in the core submesh
                if(springCount > 0)
                {
                    Element physique = vertex.getChild("PHYSIQUE");

                    if(physique != null) {

                        float physicalProperty = toFloat(physique.getValue());

                        // set the physical property in the core submesh instance
                        pCoreSubmesh.setPhysicalProperty(vertexId, physicalProperty);

                    }

                }
                
                vertexId++;

            }
            
            List<?> springs = mesh.getChildren("SPRING");
            
            int springId = 0;
            
            for(Object o2 : springs) {
                
                Element springElem = (Element) o2;
                
                String vertexIdD = springElem.getChildText("VERTEXID");
                String coefD = springElem.getChildText("COEF");
                String length = springElem.getChildText("LENGTH");
                
                CalCoreSubmesh.Spring spring = new CalCoreSubmesh.Spring();
                StringTokenizer token = new StringTokenizer(vertexIdD, " ");
                spring.vertexId0 = toInt(token.nextToken());
                spring.vertexId1 = toInt(token.nextToken());
                spring.springCoefficient = toFloat(coefD);
                spring.idleLength = toFloat(length);
                
                pCoreSubmesh.setSpring(springId, spring);
                
                springId++;
                
            }
            
        }
        
        throw new UnsupportedOperationException("The XML file format isn't supported yet by Cal3Dj." +
                "\nPlease use the binary format (.CMF, .CAF, .CSF, .CRF) instead, or implement XML" +
                "\nXML support yourself.");
        
        //return pCoreMesh;
        
    }
        
    private static void read(Element child, Vector3f posInfo) {
        
        StringTokenizer pos = new StringTokenizer(child.getValue(), " ");
        posInfo.setX( toFloat(pos.nextToken()) );
        posInfo.setY( toFloat(pos.nextToken()) );
        posInfo.setZ( toFloat(pos.nextToken()) );
        
    }

    private static float toFloat(String string) {
        
        return Float.parseFloat(string);
        
    }

    /**
     * This method has been created because Cal3D export is not valid XML.
     * It actually contains two root elements, which is illegal. The two root
     * elements are HEADER and MESH/ANIMATION/SKELETON/MATERIAL.
     * <br><br>
     * This function skips the "<?xml>" chunk, plus the HEADER element,
     * so JDOM can read it correctly.
     *  
     * @param in
     */
    private static BufferedReader getCleanXML(InputStream in, String magicNeeded) throws Exception {
        
        BufferedReader bR = new BufferedReader(new InputStreamReader(in));
        
        try {
            String line = bR.readLine();
            if(line.startsWith("<?xml version=")) {
                // Skip the HEADER line
                line = bR.readLine();
                if(line.startsWith("<HEADER")) {
                    //System.out.println("Skipped the following header : "+line);
                    StringTokenizer token = new StringTokenizer(line, "=\"");
                    token.nextToken(); // "<HEADER MAGIC"
                    String magicGot = token.nextToken();
                    if(!magicGot.equals(magicNeeded)) {
                        throw new IOException("Wrong MAGIC, should be "+magicNeeded+" instead is : "+magicGot);
                    }
                    token.nextToken(); // "VERSION"
                    int version = toInt(token.nextToken());
                    if(version < EARLIEST_COMPATIBLE_FILE_VERSION) {
                        throw new Exception("Incompatible version. Earliest compatible file version is : "+
                                EARLIEST_COMPATIBLE_FILE_VERSION+" and current file version is : "+version);
                    }
                } else {
                    //System.out.println("Can't figure out what this is : "+line);
                    Thread.dumpStack();
                }
            } else {
                //System.out.println("Can't figure out what this is : "+line);
                Thread.dumpStack();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        return bR;
    }

    private static int toInt(String string) {
        
        return Integer.parseInt(string);
        
    }

    public static CalCoreSkeleton loadCoreSkeleton(InputStream in) throws IOException {
        // open the file
        LittleEndianDataInputStream file = new LittleEndianDataInputStream(in);
        
        // check if this is a valid file
        byte [ ] magic = new byte [4];
        file.readFully(magic);
        if (!new String(magic).equals(new String(SKELETON_FILE_MAGIC))) {
            //System.out.println("Magic of the file = "+(new String(magic)));
            throw new IllegalArgumentException("File not a cal3d skeleton file (CSF)");
        }
        
        // check if the version is compatible with the library
        int version = file.readInt();
        if((version < EARLIEST_COMPATIBLE_FILE_VERSION) || (version > CURRENT_FILE_VERSION))
            throw new IllegalArgumentException("Incompatible cal3d file version");
        
        // read the number of bones
        int boneCount = file.readInt();
        if((boneCount <= 0))
            throw new IllegalArgumentException("Incompatible file format for cal3d skeleton file : boneCount = "+boneCount);
        
        
        // allocate a new core skeleton instance
        CalCoreSkeleton coreSkeleton = new CalCoreSkeleton();
        
        // load all core bones
        for(int boneId = 0; boneId < boneCount; boneId++) {
            // load the core bone
            CalCoreBone coreBone = loadCoreBones(file);
            
            // add the core bone to the core skeleton instance
            coreSkeleton.addCoreBone(coreBone);
        }
        
        // calculate state of the core skeleton
        coreSkeleton.calculateState();
        
        return coreSkeleton;
        
    }
    
    
    /*****************************************************************************/
    /** Loads a core submesh instance.
     *
     * This function loads a core submesh instance from a file stream.
     *
     * @param file The file stream to load the core submesh instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core submesh
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    protected static CalCoreSubmesh loadCoreSubmesh(DataInput file) throws IOException {
        // get the material thread id of the submesh
        int coreMaterialThreadId = file.readInt();
        
        // get the number of vertices, faces, level-of-details and springs
        int vertexCount = file.readInt();
        
        int faceCount = file.readInt();
        
        int lodCount = file.readInt();
        
        int springCount = file.readInt();
        
        // get the number of texture coordinates per vertex
        int textureCoordinateCount = file.readInt();
        
        // allocate a new core submesh instance
        CalCoreSubmesh coreSubmesh = new CalCoreSubmesh();
        
        // set the LOD step count
        coreSubmesh.setLodCount(lodCount);
        
        // set the core material id
        coreSubmesh.setCoreMaterialThreadId(coreMaterialThreadId);
        
        // reserve memory for all the submesh data
        coreSubmesh.reserve(vertexCount, textureCoordinateCount, faceCount, springCount);
        
        // load all vertices and their influences
        Vector3f vertexPosition = new Vector3f();
        Vector3f vertexNormal = new Vector3f();
        for(int vertexId = 0; vertexId < vertexCount; vertexId++) {
            CalCoreSubmesh.VertexInfo vertex = new CalCoreSubmesh.VertexInfo();
            
            // load data of the vertex
            vertexPosition.setX( file.readFloat() );
            vertexPosition.setY( file.readFloat() );
            vertexPosition.setZ( file.readFloat() );
            vertexNormal.setX( file.readFloat() );
            vertexNormal.setY( file.readFloat() );
            vertexNormal.setZ( file.readFloat() );
            vertex.collapseId = file.readInt();
            vertex.faceCollapseCount = file.readInt();
            
            // load all texture coordinates of the vertex
            int textureCoordinateId;
            for(textureCoordinateId = 0; textureCoordinateId < textureCoordinateCount; textureCoordinateId++) {
                Vector2f textureCoordinate = new Vector2f();
                
                // load data of the influence
                textureCoordinate.setX( file.readFloat() );
                
                textureCoordinate.setY( 1.0f - file.readFloat() );
                // set texture coordinate in the core submesh instance
                coreSubmesh.setTextureCoordinate(vertexId, textureCoordinateId, textureCoordinate);
            }
            
            // get the number of influences
            int influenceCount = file.readInt();
            if((influenceCount < 0))
                throw new IOException("Invalid count in cal3d mesh file");
            
            // reserve memory for the influences in the vertex
            vertex.influenceBoneIds = new int [influenceCount];
            vertex.influenceWeights = new float [influenceCount];
            
            // load all influences of the vertex
            int influenceId;
            for(influenceId = 0; influenceId < influenceCount; influenceId++) {
                // load data of the influence
                vertex.influenceBoneIds[influenceId] = file.readInt();
                vertex.influenceWeights[influenceId] = file.readFloat();
            }
            
            // set vertex in the core submesh instance
            coreSubmesh.setVertex(vertexId, vertex, vertexPosition, vertexNormal);
            
            // load the physical property of the vertex if there are springs in the core submesh
            if(springCount > 0) {
                // load data of the physical property
                float physicalProperty = file.readFloat();
                
                // set the physical property in the core submesh instance
                coreSubmesh.setPhysicalProperty(vertexId, physicalProperty);
            }
        }
        
        // load all springs
        for(int springId = 0; springId < springCount; springId++) {
            CalCoreSubmesh.Spring spring = new CalCoreSubmesh.Spring();
            
            // load data of the spring
            spring.vertexId0 = file.readInt();
            spring.vertexId1 = file.readInt();
            spring.springCoefficient = file.readFloat();
            spring.idleLength = file.readFloat();
            
            // set spring in the core submesh instance
            coreSubmesh.setSpring(springId, spring);
        }
        
        // load all faces
        for(int faceId = 0; faceId < faceCount; faceId++) {
            CalCoreSubmesh.Face face = new CalCoreSubmesh.Face();
            
            // load data of the face
            face.vertexId[0]=file.readInt();
            face.vertexId[1]=file.readInt();
            face.vertexId[2]=file.readInt();
            
            // set face in the core submesh instance
            coreSubmesh.setFace(faceId, face);
        }
        
        return coreSubmesh;
    }
    
    
    /*****************************************************************************/
    /** Loads a core track instance.
     *
     * This function loads a core track instance from a file stream.
     *
     * @param file The file stream to load the core track instance from.
     *
     * @return One of the following values:
     *         \li a pointer to the core track
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    protected static CalCoreTrack loadCoreTrack(DataInput file) throws IOException {
        // read the bone id
        int coreBoneId = file.readInt();
        if((coreBoneId < 0))
            throw new IOException("Invalid count in cal3d animation track");
        
        // allocate a new core track instance
        CalCoreTrack coreTrack = new CalCoreTrack();
        
        // link the core track to the appropriate core bone instance
        coreTrack.setCoreBoneId(coreBoneId);
        
        // read the number of keyframes
        int keyframeCount = file.readInt();
        if((keyframeCount <= 0))
            new IOException("Invalid count in cal3d animation track");
        
        // load all core keyframes
        int keyframeId;
        for(keyframeId = 0; keyframeId < keyframeCount; keyframeId++) {
            // load the core keyframe
            CalCoreKeyframe coreKeyframe = loadCoreKeyframe(file);
            
            // add the core keyframe to the core track instance
            coreTrack.addCoreKeyframe(coreKeyframe);
        }
        
        return coreTrack;
    }
}
