/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "includes.h"

/***************************************************************************
 * Containing data about how to render an object.
 ***************************************************************************/

#ifndef RENDER_DATA_H_
#define RENDER_DATA_H_

#include "Material.h"
#include "Mesh.h"

namespace mgn {

class RenderData: public HybridObject {
public:
    enum Queue {
        Background = 1000, Geometry = 2000, Transparent = 3000, Overlay = 4000
    };

    RenderData();

    Mesh* GetMesh() const {
        return mesh;
    }

    void SetMesh(Mesh* mesh) {
        this->mesh = mesh;
        surfaceDef.geo = mesh->GetGeometry();
    }

    void SetMaterial(Material* material) {
        this->material = material;
    }
    
    Material* GetMaterial() const {
        return material;
    }
    
    Material* GetMaterial() {
        return material;
    }

    bool IsVisible() const {
        return visible;
    }

    bool IsVisible() {
        return visible;
    }

    void SetVisible(bool visible) {
        this->visible = visible;
    }

    int GetRenderingOrder() const {
        return renderingOrder;
    }

    void SetRenderingOrder(int renderingOrder) {
        this->renderingOrder = renderingOrder;
    }

    bool GetDepthTest() const {
        return surfaceDef.graphicsCommand.GpuState.depthEnable;
    }

    void SetDepthTest(bool depthTest) {
        surfaceDef.graphicsCommand.GpuState.depthEnable = depthTest;
    }

    void UpdateSurfaceDef();

    void SetOpacity(float opacity) {
        this->opacity = opacity;
    }

    float GetOpacity() {
        return opacity;
    }

    const ovrSurfaceDef & GetSurfaceDef() {
        return surfaceDef;
    }

    void SetModelMatrix(const Matrix4f & modelMatrix) {
        this->modelMatrix = modelMatrix;
    }

    const Matrix4f & GetModelMatrix() {
        return modelMatrix;
    }

    const Matrix4f & GetModelMatrix() const {
        return modelMatrix;
    }

private:
    RenderData(const RenderData& renderData);
    RenderData(RenderData&& renderData);
    RenderData& operator=(const RenderData& renderData);
    RenderData& operator=(RenderData&& renderData);

private:
    static const int DEFAULT_RENDERING_ORDER = Geometry;
    Mesh* mesh;
    Material * material;
    bool visible;
    int renderingOrder;
    ovrSurfaceDef surfaceDef;
    float opacity;
    Matrix4f modelMatrix;
    Matrix4f programMatrices[2]; // 0: For left eye, 1: For right eye
    GlTexture programTexture;
    
    Matrix4f normalM = Matrix4f::Identity();
    Matrix4f topM = Matrix4f(
            1, 0, 0, 0,
            0, 0.5f, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1 );
    Matrix4f bottomM = Matrix4f(
            1, 0, 0, 0,
            0, 0.5f, 0, 0.5f,
            0, 0, 1, 0,
            0, 0, 0, 1 );
    Matrix4f leftM = Matrix4f(
            0.5f, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1 );
    Matrix4f rightM = Matrix4f(
            0.5f, 0, 0, 0.5f,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1 );

    const Matrix4f & TexmForVideo(const Material::StereoMode stereoMode, const int eye) const;
};

inline bool compareRenderData(RenderData* i, RenderData* j) {
    return i->GetRenderingOrder() < j->GetRenderingOrder();
}

}
#endif
